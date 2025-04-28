package gdgoc.tuk.official.email.service;

import gdgoc.tuk.official.email.dto.EmailVerificationRequest;
import gdgoc.tuk.official.email.exception.NotVerifiedEmailException;
import gdgoc.tuk.official.email.repository.VerificationCodeRedisRepository;
import gdgoc.tuk.official.google.service.EmailSender;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmailServiceTest {

    private final EmailSender testEmailSender = new TestEmailSender();
    private final VerificationCodeRedisRepository repository = new TestVerificationCodeRedisRepositoryImpl();
    private final EmailService emailService = new EmailService(testEmailSender,repository);

    @Test
    @DisplayName("인증코드가 같으면 인증에 성공한다.")
    void checkVerificationCodeIsValid() {
        // given
        String email = "gdgoc@tukorea.ac.kr";
        String validCode = "1234";
        Integer timeOut = 5;
        repository.saveVerificationCode(email, validCode,timeOut);

        EmailVerificationRequest request = new EmailVerificationRequest(validCode, email);
        // when & then
        Assertions.assertThatCode(()->emailService.verifyEmail(request)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("인증코드가 다르면 인증에 예외를 발생시킨다.")
    void checkVerificationCodeIsNotValid() {
        // given
        String email = "gdgoc@tukorea.ac.kr";
        String validCode = "1234";
        String invalidCode = "2345";
        Integer timeOut = 5;
        repository.saveVerificationCode(email,validCode,timeOut);

        EmailVerificationRequest request = new EmailVerificationRequest(invalidCode, email);
        // when & then
        Assertions.assertThatCode(()->emailService.verifyEmail(request)).isInstanceOf(NotVerifiedEmailException.class);
    }

}
