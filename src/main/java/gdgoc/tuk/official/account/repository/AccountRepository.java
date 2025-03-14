package gdgoc.tuk.official.account.repository;

import gdgoc.tuk.official.account.domain.Accounts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Accounts, Long> {

    Optional<Accounts> findByEmail(String email);

    boolean existsByEmail(String email);
}
