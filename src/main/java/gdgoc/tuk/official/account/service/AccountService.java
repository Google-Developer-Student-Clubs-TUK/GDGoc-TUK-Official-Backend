package gdgoc.tuk.official.account.service;

import gdgoc.tuk.official.account.domain.Account;
import gdgoc.tuk.official.account.domain.Role;
import gdgoc.tuk.official.account.dto.RegisterRequest;
import gdgoc.tuk.official.account.dto.RegisterResponse;
import gdgoc.tuk.official.account.exception.DuplicatedRegisterException;
import gdgoc.tuk.official.account.repository.AccountRepository;
import gdgoc.tuk.official.applicant.service.ApplicantService;
import gdgoc.tuk.official.generationmember.exception.AccountNotFoundException;
import gdgoc.tuk.official.global.ErrorCode;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final ApplicantService applicantService;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    public RegisterResponse createAccount(final RegisterRequest request) {
        checkDuplicateAccount(request.email());
        applicantService.checkAcceptedApplicant(request.email());
        final String encodedPassword = passwordEncoder.encode(request.password());
        Account account = new Account(request.email(), encodedPassword, Role.ROLE_ORGANIZER);
        accountRepository.save(account);
        return new RegisterResponse(account.getId(), account.getEmail());
    }

    private void checkDuplicateAccount(final String email) {
        if (accountRepository.existsByEmail(email)) {
            throw new DuplicatedRegisterException(ErrorCode.DUPLICATED_ACCOUNT);
        }
    }

    public Account getAccountByEmail(final String email) {
        return accountRepository
                .findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND));
    }
}
