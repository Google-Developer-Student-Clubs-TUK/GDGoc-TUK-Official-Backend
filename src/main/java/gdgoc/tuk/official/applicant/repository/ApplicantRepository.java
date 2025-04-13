package gdgoc.tuk.official.applicant.repository;

import gdgoc.tuk.official.applicant.domain.Applicant;
import gdgoc.tuk.official.applicant.domain.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

    boolean existsByApplicationStatusAndEmail(ApplicationStatus applicationStatus, String email);

    Page<Applicant> findByApplicationStatus(ApplicationStatus applicationStatus, Pageable pageable);
}
