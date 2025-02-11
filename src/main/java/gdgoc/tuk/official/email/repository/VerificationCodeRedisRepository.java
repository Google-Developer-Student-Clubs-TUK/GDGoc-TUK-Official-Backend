package gdgoc.tuk.official.email.repository;

import java.time.Duration;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class VerificationCodeRedisRepository {

    private final RedisTemplate<String,Object> redisTemplate;

    public void saveVerificationCode(final String email,final String code, final Integer timeout){
        redisTemplate.delete(email);
        redisTemplate.opsForValue().set(email,code, Duration.ofMinutes(20));
    }

    public Optional<Object> findVerificationCode(final String email){
        return Optional.of(redisTemplate.opsForValue().get(email));
    }
}
