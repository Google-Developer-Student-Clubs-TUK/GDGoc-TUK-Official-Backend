package gdgoc.tuk.official.email.service;

import gdgoc.tuk.official.email.dto.EmailVerificationRequest;
import gdgoc.tuk.official.email.exception.NotVerifiedEmailException;
import gdgoc.tuk.official.email.repository.VerificationCodeRedisRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmailServiceTest {

    private TestEmailSender testEmailSender;
    private VerificationCodeRedisRepository repository;
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        testEmailSender = new TestEmailSender();
        repository = new TestVerificationCodeRedisRepositoryImpl();
        emailService = new EmailService(testEmailSender, repository);
    }

    @Test
    @DisplayName("인증코드가 같으면 인증에 성공한다.")
    void checkVerificationCodeIsValid() {
        // given
        String email = "gdgoc@tukorea.ac.kr";
        String validCode = "1234";
        int timeOut = 5;
        repository.saveVerificationCode(email, validCode, timeOut);

        EmailVerificationRequest request = new EmailVerificationRequest(validCode, email);

        // when & then
        Assertions.assertThatCode(() -> emailService.verifyEmail(request))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("인증코드가 다르면 인증에 예외를 발생시킨다.")
    void checkVerificationCodeIsNotValid() {
        // given
        String email = "gdgoc@tukorea.ac.kr";
        String validCode = "1234";
        String invalidCode = "2345";
        int timeOut = 5;
        repository.saveVerificationCode(email, validCode, timeOut);

        EmailVerificationRequest request = new EmailVerificationRequest(invalidCode, email);

        // when & then
        Assertions.assertThatExceptionOfType(NotVerifiedEmailException.class)
                .isThrownBy(() -> emailService.verifyEmail(request));
    }

    @Test
    @DisplayName("인증 메일 발송 시 EmailSender가 호출된다.")
    void sendVerificationEmail() {

        // given
        String email = "gdgoc@tukorea.ac.kr";

        // when
        emailService.sendVerificationMail(email);

        // then
        Assertions.assertThat(testEmailSender.sentCount()).isEqualTo(1);
        Assertions.assertThat(testEmailSender.lastSentEmail().getTo()).isEqualTo(email);
    }
}