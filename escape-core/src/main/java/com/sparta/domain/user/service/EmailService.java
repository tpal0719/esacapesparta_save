package com.sparta.domain.user.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;

import com.sparta.domain.user.entity.User;
import com.sparta.domain.user.entity.UserType;
import com.sparta.domain.user.repository.EmailRepository;
import com.sparta.domain.user.repository.UserRepository;
import com.sparta.global.exception.customException.EmailException;
import com.sparta.global.exception.customException.UserException;
import com.sparta.global.exception.errorCode.EmailErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final EmailRepository emailRepository;
    private final RedisTemplate redisTemplate;
    private final UserRepository userRepository;
    public static final String MAIL_TITLE_CERTIFICATION = "이메일 인증입니다";


    // TODO : 이메일 인증번호 발송 메서드
    public String sendEmailForCertification(String email) throws NoSuchAlgorithmException, MessagingException {
        String certificationNumber = createCertificationNumber();
        String content = String.format("인증 번호 : " + certificationNumber + "\n인증코드를 5분 이내에 입력해주세요.");
        emailRepository.saveCertificationNumber(email, certificationNumber);
        sendMail(email, content);
        return email;
    }

    // TODO : 이메일 발송 메서드
    private void sendMail(String email, String content) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setTo(email);
        helper.setSubject(MAIL_TITLE_CERTIFICATION);
        helper.setText(content);
        mailSender.send(mimeMessage);
    }

    // TODO : 이메일 인증번호 확인 메서드
    public void verifyEmail(String email, String certificationNumber) {
        if (!emailRepository.getCertificationNumber(email).equals(certificationNumber)) {
            throw new EmailException(EmailErrorCode.EMAIL_VERIFICATION_CODE_MISMATCH);
        }
        emailRepository.removeCertificationNumber(email);
    }

    // TODO : 인증번호 생성 메서드
    public String createCertificationNumber() throws NoSuchAlgorithmException {
        String result;
        do {
            int num = SecureRandom.getInstanceStrong().nextInt(999999);
            result = String.valueOf(num);
        } while (result.length() != 6);

        return result;
    }

    // TODO : 초대코드 생성 메서드
    public String createInviteCode(UserType role) {
        String prefix = switch (role) {
            case ADMIN -> "1";
            case MANAGER -> "2";
            default -> "0";
        };
        return prefix + UUID.randomUUID().toString().substring(1);
    }

    // TODO : 권한을 결정하는 초대코드 이메일 발송 메서드
    public void sendInviteCode(String channel, String inviteCode) {
        String message = "초대 코드: " + " " + inviteCode;
        redisTemplate.convertAndSend("spartaproject13@gmail.com"+ "ata01@naver.com", channel + ":" + message);
    }

    // TODO : 초대코드 검증 메서드
    public UserType validateInviteCode(String code) {
        User user = userRepository.findByInviteCodeOrElseThrow(code);
        if (user.getInviteCodeExpirationTime().isBefore(LocalDateTime.now())) {
            throw new UserException(EmailErrorCode.EMAIL_VERIFICATION_CODE_MISMATCH);
        }
        return user.getUserType();
    }
}