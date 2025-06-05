package gdgoc.tuk.official.global.async;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import java.util.concurrent.ThreadPoolExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AsyncThreadPoolMetrics {

    private final ThreadPoolTaskExecutor executor;
    private final MeterRegistry registry;

    @PostConstruct
    public void bindMetrics() {
        ThreadPoolExecutor tp = executor.getThreadPoolExecutor();

        Gauge.builder("async.executor.active.thread", tp, ThreadPoolExecutor::getActiveCount)
            .description("Active async threads")
            .register(registry);

        Gauge.builder("async.executor.queue.size", tp, e -> e.getQueue().size())
            .description("Async executor queue size")
            .register(registry);
    }
}
