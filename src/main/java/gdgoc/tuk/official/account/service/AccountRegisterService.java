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
    private final ApplicantRepository applicantRepository;
    private final GenerationMemberService generationMemberService;

    public RegisterResponse createAccount(final RegisterRequest request) {
        checkDuplicateAccount(request.email());
        Applicant acceptedApplicant = getAcceptedApplicant(ApplicationStatus.ACCEPTED,
            request.email());
        final String encodedPassword = passwordEncoder.encode(request.password());
        Accounts accounts = new Accounts(request.email(), encodedPassword, Role.ROLE_ORGANIZER);
        accountRepository.save(accounts);
        generationMemberService.createGenerationMemberForNewAccount(acceptedApplicant, accounts);
        return new RegisterResponse(accounts.getId(), accounts.getEmail());
    }

    public Applicant getAcceptedApplicant(final ApplicationStatus applicationStatus,final String email) {
        return applicantRepository.findByApplicationStatusAndEmail(applicationStatus,email)
            .orElseThrow(()->new NotAcceptedApplicantException(ErrorCode.NOT_ACCEPTED_APPLICANT));
    }

    private void checkDuplicateAccount(final String email) {
        if (accountRepository.existsByEmail(email)) {
            throw new DuplicatedRegisterException(ErrorCode.DUPLICATED_ACCOUNT);
        }
    }
}
