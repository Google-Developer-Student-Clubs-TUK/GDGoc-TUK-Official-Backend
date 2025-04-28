package gdgoc.tuk.official.applicant.service;

import gdgoc.tuk.official.applicant.domain.ApplicationStatus;
import gdgoc.tuk.official.applicant.repository.ApplicantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicantValidator {

    private final ApplicantRepository applicantRepository;

    public boolean isAlreadyApplied(final String email) {
        return applicantRepository.existsByApplicationStatusAndEmail(
            ApplicationStatus.PENDING, email);
    }
}
