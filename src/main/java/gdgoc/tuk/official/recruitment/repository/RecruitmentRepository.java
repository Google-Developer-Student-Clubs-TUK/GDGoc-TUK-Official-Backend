package gdgoc.tuk.official.recruitment.repository;

import gdgoc.tuk.official.recruitment.domain.Recruitment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {

    boolean existsByGeneration(String generation);

    boolean existsByCloseAtIsAfter(LocalDateTime now);

    @Query("select r from Recruitment r where :now BETWEEN r.openAt and r.closeAt")
    Optional<Recruitment> findByBetweenOpenAtAndCloseAt(LocalDateTime now);

    @Query("select r from Recruitment r where r.closeAt < :now")
    List<Recruitment> findGenerationByLocalTime(LocalDateTime now);
}
