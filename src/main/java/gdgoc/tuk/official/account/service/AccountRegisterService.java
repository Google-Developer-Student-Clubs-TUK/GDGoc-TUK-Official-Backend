package gdgoc.tuk.official.account.service;

import gdgoc.tuk.official.account.domain.Accounts;
import gdgoc.tuk.official.account.domain.Role;
import gdgoc.tuk.official.account.repository.AccountRepository;
import gdgoc.tuk.official.applicant.domain.Applicant;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountRegisterService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    public Accounts createOrFindAccount(final Applicant applicant, final Role role) {
        Accounts accounts =
                accountRepository
                        .findByEmail(applicant.getEmail())
                        .orElse(createAccounts(applicant, role));
        accounts.changeRole(role);
        return accounts;
    }

    private Accounts createAccounts(final Applicant applicant, final Role role) {
        final String encodedPassword = passwordEncoder.encode(applicant.getStudentNumber());
        Accounts accounts = new Accounts(applicant.getEmail(), encodedPassword, role);
        accountRepository.save(accounts);
        return accounts;
    }
}
