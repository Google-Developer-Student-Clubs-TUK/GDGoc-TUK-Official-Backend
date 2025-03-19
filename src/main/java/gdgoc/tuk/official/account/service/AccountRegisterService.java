package gdgoc.tuk.official.account.service;

import gdgoc.tuk.official.account.domain.Accounts;
import gdgoc.tuk.official.account.domain.Role;
import gdgoc.tuk.official.account.dto.RegisterRequest;
import gdgoc.tuk.official.account.dto.RegisterResponse;
import gdgoc.tuk.official.account.exception.DuplicatedRegisterException;
import gdgoc.tuk.official.account.repository.AccountRepository;
import gdgoc.tuk.official.applicant.domain.Applicant;
import gdgoc.tuk.official.applicant.domain.ApplicationStatus;
import gdgoc.tuk.official.applicant.exception.NotAcceptedApplicantException;
import gdgoc.tuk.official.applicant.repository.ApplicantRepository;
import gdgoc.tuk.official.generationmember.service.GenerationMemberService;
import gdgoc.tuk.official.global.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountRegisterService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    public Accounts createOrFindAccount(final Applicant applicant, final Role role) {
        return accountRepository.findByEmail(applicant.getEmail())
            .orElse(createAccounts(applicant,role));
    }

    private Accounts createAccounts(final Applicant applicant,final Role role){
        final String encodedPassword = passwordEncoder.encode(applicant.getStudentNumber());
        Accounts accounts = new Accounts(applicant.getEmail(), encodedPassword, role);
        accountRepository.save(accounts);
        return accounts;
    }
}
