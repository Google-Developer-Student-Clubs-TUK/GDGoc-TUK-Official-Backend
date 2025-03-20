package gdgoc.tuk.official.applicant.service;

import gdgoc.tuk.official.account.domain.Accounts;
import gdgoc.tuk.official.account.service.AccountRegisterService;
import gdgoc.tuk.official.answer.dto.RequiredAnswer;
import gdgoc.tuk.official.applicant.domain.Applicant;
import gdgoc.tuk.official.applicant.domain.ApplicationStatus;
import gdgoc.tuk.official.applicant.dto.ApplicantResponse;
import gdgoc.tuk.official.applicant.dto.ApplicantRoleRequest;
import gdgoc.tuk.official.applicant.exception.ApplicantNotFoundException;
import gdgoc.tuk.official.applicant.repository.ApplicantRepository;
import gdgoc.tuk.official.applicant.service.mapper.ApplicantMapper;
import gdgoc.tuk.official.email.service.EmailService;
import gdgoc.tuk.official.generationmember.service.GenerationMemberService;
import gdgoc.tuk.official.global.ErrorCode;

import jakarta.validation.constraints.Email;
import java.io.IOException;
import javax.mail.MessagingException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final ApplicantMapper applicantMapper;
    private final GenerationMemberService generationMemberService;
    private final AccountRegisterService accountRegisterService;
    private final EmailService emailService;

    @Transactional
    public Applicant saveApplicant(final RequiredAnswer requiredAnswer, final String generation) {
        final Applicant applicant = applicantMapper.toApplicant(requiredAnswer, generation);
        applicantRepository.save(applicant);
        return applicant;
    }

    public boolean isAlreadyApplied(final String email){
        return applicantRepository.existsByApplicationStatusAndEmail(ApplicationStatus.PENDING,
            email);
    }

    public Applicant getApplicantById(final Long applicantId) {
        return applicantRepository
                .findById(applicantId)
                .orElseThrow(() -> new ApplicantNotFoundException(ErrorCode.APPLICANT_NOT_FOUND));
    }

    public ApplicantResponse findAllApplicants() {
        return applicantMapper.toApplicantResponse(
                applicantRepository.findByApplicationStatus(ApplicationStatus.PENDING));
    }

    @Transactional
    public void approve(final Long applicantId,final ApplicantRoleRequest request)
        throws MessagingException, IOException {
        Applicant applicant = getApplicantById(applicantId);
        Accounts accounts = accountRegisterService.createOrFindAccount(applicant,request.role());
        generationMemberService.createGenerationMember(applicant,accounts);
        applicant.approve();
        emailService.sendWelcomMail(accounts.getEmail(),applicant.getGeneration());
    }

    @Transactional
    public void reject(final Long applicantId) {
        Applicant applicant = getApplicantById(applicantId);
        applicant.reject();
    }
}
