package gdgoc.tuk.official.email.repository;

import java.time.Duration;
import java.util.Optional;


public interface VerificationCodeRedisRepository {

    void saveVerificationCode(final String email, final String code, final Integer timeout);

    Optional<Object> findVerificationCode(final String email);
}
