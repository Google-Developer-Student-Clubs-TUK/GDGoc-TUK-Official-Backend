package gdgoc.tuk.official.applicant.repository;

import gdgoc.tuk.official.applicant.domain.Applicant;
import gdgoc.tuk.official.applicant.domain.ApplicationStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant,Long> {

    boolean existsByApplicationStatusAndEmail(ApplicationStatus applicationStatus,String email);

    Optional<Applicant> findByEmail(String email);
}
