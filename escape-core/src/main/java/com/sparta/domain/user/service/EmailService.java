package com.sparta.domain.user.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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
        if (! emailRepository.getCertificationNumber(email).equals(certificationNumber)) {
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
}