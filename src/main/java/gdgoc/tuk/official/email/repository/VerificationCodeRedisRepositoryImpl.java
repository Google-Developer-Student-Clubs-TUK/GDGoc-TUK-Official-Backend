package gdgoc.tuk.official.email.repository;

import java.time.Duration;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class VerificationCodeRedisRepositoryImpl implements VerificationCodeRedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    public void saveVerificationCode(final String email, final String code, final Integer timeout) {
        redisTemplate.delete(email);
        redisTemplate.opsForValue().set(email, code, Duration.ofMinutes(timeout));
    }

    public Optional<Object> findVerificationCode(final String email) {
        return Optional.of(redisTemplate.opsForValue().get(email));
    }
}
