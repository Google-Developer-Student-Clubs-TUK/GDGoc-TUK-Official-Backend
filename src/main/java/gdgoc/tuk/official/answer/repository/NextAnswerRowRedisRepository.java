package gdgoc.tuk.official.answer.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class NextAnswerRowRedisRepository {

  private final RedisTemplate<String, Object> redisTemplate;
  private static final String INITIAL_NEXT_ROW = "1";

  public Object findNextRow(final String generation) {
    return redisTemplate.opsForValue().get(generation);
  }

  public void increaseNextRow(final String generation) {
    redisTemplate.opsForValue().increment(generation);
  }

  public void saveNewGeneration(final String generation) {
    redisTemplate.delete(generation);
    redisTemplate.opsForValue().set(generation,INITIAL_NEXT_ROW);
  }
}
