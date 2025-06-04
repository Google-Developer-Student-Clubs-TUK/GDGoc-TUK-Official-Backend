package gdgoc.tuk.official.google.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailRateLimiter {

    private final Bucket bucket;

    public EmailRateLimiterService() {
        Refill refill = Refill.intervally(10, Duration.ofSeconds(15));
        Bandwidth limit = Bandwidth.classic(10, refill);
        this.bucket = Bucket4j.builder()
            .addLimit(limit)
            .build();
    }

    public boolean tryConsume() {
        return bucket.tryConsume(1);
    }
}
