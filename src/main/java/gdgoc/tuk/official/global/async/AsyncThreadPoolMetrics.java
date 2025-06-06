package gdgoc.tuk.official.global.async;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

@Component
public class AsyncThreadPoolMetrics {

    private final ThreadPoolTaskExecutor executor;
    private final MeterRegistry registry;

    public AsyncThreadPoolMetrics(
            @Qualifier("asyncThreadPoolTaskExecutor") final ThreadPoolTaskExecutor executor,
            final MeterRegistry registry) {
        this.executor = executor;
        this.registry = registry;
    }

    @PostConstruct
    public void bindMetrics() {
        ThreadPoolExecutor tp = executor.getThreadPoolExecutor();

        Gauge.builder("async.executor.active.thread", tp, ThreadPoolExecutor::getActiveCount)
                .description("Active async threads")
                .register(registry);

        Gauge.builder("async.executor.live.thread", tp, ThreadPoolExecutor::getPoolSize)
                .description("Thread Pool Size")
                .register(registry);

        Gauge.builder("async.executor.task.complete", tp, ThreadPoolExecutor::getCompletedTaskCount)
                .description("Complete Task")
                .register(registry);

        Gauge.builder("async.executor.queue.size", tp, e -> e.getQueue().size())
                .description("Async executor queue size")
                .register(registry);
    }
}
