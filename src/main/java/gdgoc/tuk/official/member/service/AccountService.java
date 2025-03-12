package gdgoc.tuk.official.member.service;

import gdgoc.tuk.official.applicant.domain.Applicant;
import gdgoc.tuk.official.applicant.service.ApplicantService;
import gdgoc.tuk.official.member.domain.Account;
import gdgoc.tuk.official.member.dto.RegisterRequest;
import gdgoc.tuk.official.member.service.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final ApplicantService applicantService;
    private final PasswordEncoder passwordEncoder;
    private final MemberMapper memberMapper;

    public Account createFromRequest(final RegisterRequest request) {
        applicantService.checkAcceptedApplicant(request.email());
        final Applicant applicant = applicantService.getApplicant(request.email());
        final String encodedPassword = passwordEncoder.encode(request.password());
        return memberMapper.fromApplicant(applicant, encodedPassword);
    }
}
