package gdgoc.tuk.official.answer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NextAnswerRowService {

  private final RedisTemplate<String, Object> redisTemplate;
  private static final String INITIAL_NEXT_ROW = "1";

  public Object getNextRow(final String generation) {
    return redisTemplate.opsForValue().get(generation);
  }

  public void increaseNextRow(final String generation) {
    redisTemplate.opsForValue().increment(generation);
  }

  public void createNewGeneration(final String generation) {
    redisTemplate.delete(generation);
    redisTemplate.opsForValue().set(generation,INITIAL_NEXT_ROW);
  }
}
