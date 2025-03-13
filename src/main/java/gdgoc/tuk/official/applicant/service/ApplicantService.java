package gdgoc.tuk.official.applicant.service;

import gdgoc.tuk.official.answer.dto.RequiredAnswer;
import gdgoc.tuk.official.applicant.domain.Applicant;
import gdgoc.tuk.official.applicant.domain.ApplicationStatus;
import gdgoc.tuk.official.applicant.dto.ApplicantResponse;
import gdgoc.tuk.official.applicant.exception.ApplicantNotFoundException;
import gdgoc.tuk.official.applicant.exception.NotAcceptedApplicantException;
import gdgoc.tuk.official.applicant.repository.ApplicantRepository;
import gdgoc.tuk.official.applicant.service.mapper.ApplicantMapper;
import gdgoc.tuk.official.generationmember.service.GenerationMemberService;
import gdgoc.tuk.official.global.ErrorCode;

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

    @Transactional
    public void saveApplicant(final RequiredAnswer requiredAnswer, final String generation) {
        final Applicant applicant = applicantMapper.toApplicant(requiredAnswer, generation);
        applicantRepository.save(applicant);
    }

    public Applicant getApplicantByEmail(final String email) {
        return applicantRepository
                .findByEmail(email)
                .orElseThrow(() -> new ApplicantNotFoundException(ErrorCode.APPLICANT_NOT_FOUND));
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

    public void checkAcceptedApplicant(final String email) {
        if (!applicantRepository.existsByApplicationStatusAndEmail(
                ApplicationStatus.ACCEPTED, email)) {
            throw new NotAcceptedApplicantException(ErrorCode.NOT_ACCEPTED_APPLICANT);
        }
    }

    @Transactional
    public void approve(final Long applicantId) {
        Applicant applicant = getApplicantById(applicantId);
        generationMemberService.createGenerationMember(applicant);
        applicant.approve();
    }

    @Transactional
    public void reject(final Long applicantId) {
        Applicant applicant = getApplicantById(applicantId);
        applicant.approve();
    }
}
