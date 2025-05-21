package gdgoc.tuk.official.email.service;

import gdgoc.tuk.official.email.dto.EmailVerificationRequest;
import gdgoc.tuk.official.email.exception.NotVerifiedEmailException;
import gdgoc.tuk.official.email.repository.VerificationCodeRedisRepository;
import gdgoc.tuk.official.email.template.VerificationCodeMailTemplate;
import gdgoc.tuk.official.email.template.WelcomMailTemplate;
import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.google.service.EmailSender;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailSender emailSender;
    private final VerificationCodeRedisRepository redisRepository;

    public void sendVerificationMail(final String receiverEmail) {
        final String code = VerificationCodeGenerator.generate();
        redisRepository.saveVerificationCode(receiverEmail, code, 30);
        final String mailBody = VerificationCodeMailTemplate.code(code);
        emailSender.send(receiverEmail, VerificationCodeMailTemplate.TITLE, mailBody);
    }

    public void sendWelcomeMail(final String receiverEmail, final String generation) {
        final String mailBody = WelcomMailTemplate.body(generation);
        final String title = WelcomMailTemplate.title(generation);
        emailSender.send(receiverEmail, title, mailBody);
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
