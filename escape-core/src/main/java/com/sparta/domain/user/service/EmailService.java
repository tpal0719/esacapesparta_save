package com.sparta.domain.user.service;

import java.security.SecureRandom;
import java.util.UUID;

import com.sparta.domain.user.dto.request.CertificateRequestDto;
import com.sparta.domain.user.entity.UserType;
import com.sparta.domain.user.repository.EmailRepository;
import com.sparta.global.exception.customException.EmailException;
import com.sparta.global.exception.errorCode.EmailErrorCode;
import lombok.extern.slf4j.Slf4j;
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
//    private final RedisTemplate redisTemplate;
//    private final UserRepository userRepository;
    public static final String MAIL_TITLE_CERTIFICATION = "이메일 인증입니다";


    // TODO : 이메일 인증번호 발송 메서드
    public String sendEmailForCertification(CertificateRequestDto requestDto) throws MessagingException {
        String email = requestDto.getEmail();
        String certificationNumber = createCertificateCode(requestDto.getUserType());

        String content = String.format("인증 코드 : " + certificationNumber + "\n인증 코드를 5분 이내에 입력해주세요.");
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
//        emailRepository.removeCertificationNumber(email);
    }


    // TODO : 인증 코드 생성 메서드
    public String createCertificateCode(UserType userType) {
        String prefix;
        switch (userType) {
            case ADMIN:
                prefix = "1";
                break;
            case MANAGER:
                prefix = "2";
                break;
            default:
                prefix = "0";
                break;
        }
        SecureRandom secureRandom = new SecureRandom();
        int randomNumber = secureRandom.nextInt(900000) + 10000; // 100000 ~ 999999 범위의 6자리 난수 생성
        return prefix + randomNumber;
    }

    public UserType determineUserTypeFromCertificateCode(String code) {
        String prefix = code.substring(0, 1);
        return switch (prefix) {
            case "1" -> UserType.ADMIN;
            case "2" -> UserType.MANAGER;
            default -> UserType.USER;
        };
    }

//    // TODO : 인증번호 생성 메서드
//    public String createCertificationNumber() throws NoSuchAlgorithmException {
//        String result;
//        do {
//            int num = SecureRandom.getInstanceStrong().nextInt(999999);
//            result = String.valueOf(num);
//        } while (result.length() != 6);
//
//        return result;
//    }
//
//
//    // TODO : 권한을 결정하는 초대코드 이메일 발송 메서드
//    public void sendInviteCode(String email, String inviteCode) throws MessagingException {
//        // 실제 이메일 발송
//        sendInviteCodeEmail(email, inviteCode);
//
//        // Redis 메세지 브로커
//        String message = "초대 코드: " + inviteCode;
//        redisTemplate.convertAndSend("spartaprocject13@gmail.com", email + ":" + message);
//    }
//
//    // 실제 이메일 발송 메서드
//    private void sendInviteCodeEmail(String email, String inviteCode) throws MessagingException {
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//        helper.setFrom("spartaproject13@gmail.com");
//        helper.setTo(email);
//        helper.setSubject("초대 코드");
//        helper.setText("초대 코드: " + inviteCode);
//
//        mailSender.send(message);
//    }
}