package gdgoc.tuk.official.applicant.service;

import gdgoc.tuk.official.applicant.domain.Applicant;
import gdgoc.tuk.official.applicant.domain.ApplicationStatus;
import gdgoc.tuk.official.applicant.exception.ApplicantNotFoundException;
import gdgoc.tuk.official.applicant.exception.NotAcceptedApplicantException;
import gdgoc.tuk.official.applicant.repository.ApplicantRepository;
import gdgoc.tuk.official.global.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicantService {

  private final ApplicantRepository applicantRepository;

  @Transactional
  public void saveApplicant() {

  }

  public Applicant getApplicant(final String email) {
    return applicantRepository
        .findByEmail(email)
        .orElseThrow(() -> new ApplicantNotFoundException(ErrorCode.APPLICANT_NOT_FOUND));
  }

  public void checkAcceptedApplicant(final String email) {
    if (!applicantRepository.existsByApplicationStatusAndEmail(ApplicationStatus.ACCEPTED, email)) {
      throw new NotAcceptedApplicantException(ErrorCode.NOT_ACCEPTED_APPLICANT);
    }
  }
}
