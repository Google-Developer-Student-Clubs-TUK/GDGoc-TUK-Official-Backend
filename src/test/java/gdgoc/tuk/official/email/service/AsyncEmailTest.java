package gdgoc.tuk.official.email.service;

import gdgoc.tuk.official.config.TestConfig;
import gdgoc.tuk.official.google.service.EmailSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestConfig.class)
public class AsyncEmailTest {

    @Autowired
    EmailSender emailSender;

    @Test
    void asyncTest() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            emailSender.send("a", "b","c");
        }
        Thread.sleep(100);
    }
}
