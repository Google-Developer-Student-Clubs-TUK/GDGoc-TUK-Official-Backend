package gdgoc.tuk.official.email.service;

import gdgoc.tuk.official.email.dto.EmailVerificationRequest;
import gdgoc.tuk.official.email.exception.NotVerifiedEmailException;
import gdgoc.tuk.official.email.repository.VerificationCodeRedisRepository;
import gdgoc.tuk.official.email.template.VerificationCodeMailTemplate;
import gdgoc.tuk.official.email.template.WelcomMailTemplate;
import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.google.service.GmailService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

import javax.mail.MessagingException;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final GmailService gmailService;
    private final VerificationCodeRedisRepository redisRepository;

    @Value("${google.gmail.code-timeout}")
    private Integer codeTimeout;

    public void sendVerificationMail(final String receiverEmail)
            throws MessagingException, IOException {
        final String code = VerificationCodeGenerator.generate();
        redisRepository.saveVerificationCode(receiverEmail, code, codeTimeout);
        final String mailBody = VerificationCodeMailTemplate.code(code);
        gmailService.sendEmail(receiverEmail, VerificationCodeMailTemplate.TITLE, mailBody);
    }

    public void sendWelcomMail(final String receiverEmail, final String generation)
            throws MessagingException, IOException {
        final String mailBody = WelcomMailTemplate.body(generation);
        final String title = WelcomMailTemplate.title(generation);
        gmailService.sendEmail(receiverEmail, title, mailBody);
    }

    public void verifyEmail(final EmailVerificationRequest request) {
        final String savedCode =
                (String)
                        redisRepository
                                .findVerificationCode(request.email())
                                .orElseThrow(
                                        () ->
                                                new NotVerifiedEmailException(
                                                        ErrorCode.NOT_VERIFIED_EMAIL));
        if (isNotMatches(request.code(), savedCode)) {
            throw new NotVerifiedEmailException(ErrorCode.INVALID_CODE);
        }
    }

    private boolean isNotMatches(final String requestCode, final String savedCode) {
        return !requestCode.equals(savedCode);
    }
}
