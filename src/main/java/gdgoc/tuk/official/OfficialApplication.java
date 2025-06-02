package gdgoc.tuk.official;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class OfficialApplication {

    public static void main(String[] args) {
        SpringApplication.run(OfficialApplication.class, args);
    }
}
