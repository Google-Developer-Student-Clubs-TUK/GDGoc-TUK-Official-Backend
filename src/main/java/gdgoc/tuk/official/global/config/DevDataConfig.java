package gdgoc.tuk.official.global.config;


import gdgoc.tuk.official.account.domain.Accounts;
import gdgoc.tuk.official.account.domain.Role;
import gdgoc.tuk.official.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DevDataConfig implements CommandLineRunner {

    private final AccountRepository accountRepository;  // 테스트 데이터 넣을 Repository 주입

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        accountRepository.save(new Accounts("test", "test", Role.ROLE_LEADER));
    }
}