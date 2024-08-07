package com.sparta.domain.user.service;

import com.sparta.domain.user.dto.request.CertificateRequestDto;
import com.sparta.domain.user.entity.UserType;
import com.sparta.domain.user.repository.EmailRepository;
import com.sparta.global.exception.customException.EmailException;
import com.sparta.global.exception.errorCode.EmailErrorCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final EmailRepository emailRepository;

    public static final String MAIL_TITLE_CERTIFICATION = "이메일 인증입니다";

    public String sendEmailForCertification(CertificateRequestDto requestDto) throws MessagingException {
        String email = requestDto.getEmail();
        String certificationNumber = createCertificateCode(requestDto.getUserType());

        String content = String.format("인증 코드 : " + certificationNumber + "\n인증 코드를 5분 이내에 입력해주세요.");
        emailRepository.saveCertificationNumber(email, certificationNumber);
        sendMail(email, content);
        return email;
    }

    private void sendMail(String email, String content) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setTo(email);
        helper.setSubject(MAIL_TITLE_CERTIFICATION);
        helper.setText(content);
        mailSender.send(mimeMessage);
    }

    public void verifyEmail(String email, String certificationNumber) {
        if (!emailRepository.getCertificationNumber(email).equals(certificationNumber)) {
            throw new EmailException(EmailErrorCode.EMAIL_VERIFICATION_CODE_MISMATCH);
        }
    }

    public String createCertificateCode(UserType userType) {
        String prefix = switch (userType) {
            case ADMIN -> "1";
            case MANAGER -> "2";
            default -> "0";
        };
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
}