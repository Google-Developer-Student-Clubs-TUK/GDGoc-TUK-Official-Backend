package gdgoc.tuk.official.global.async;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncThreadPoolConfiguration implements AsyncConfigurer {

//    @Bean(name = "asyncThreadPoolTaskExecutor")
//    public ThreadPoolTaskExecutor asyncThreadPoolTaskExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(13);
//        executor.setMaxPoolSize(13);
//        executor.setQueueCapacity(Integer.MAX_VALUE);
//        executor.setKeepAliveSeconds(60);
//        executor.setThreadNamePrefix("async-executor-");
//        executor.initialize();
//        return executor;
//    }

    @Bean(name = "asyncThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor asyncThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(50);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("async-executor-");
        executor.initialize();
        return executor;
    }

    @Override
    public Executor getAsyncExecutor() {
        return asyncThreadPoolTaskExecutor();
    }
}
