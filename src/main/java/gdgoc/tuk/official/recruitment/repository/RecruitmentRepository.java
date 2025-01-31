package gdgoc.tuk.official.recruitment.repository;

import gdgoc.tuk.official.recruitment.domain.Recruitment;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {

  boolean existsByGeneration(Integer generation);

  boolean existsByCloseAtIsAfter(LocalDateTime now);
}
