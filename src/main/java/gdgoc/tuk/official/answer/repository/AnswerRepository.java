package gdgoc.tuk.official.answer.repository;

import gdgoc.tuk.official.answer.domain.Answer;
import gdgoc.tuk.official.applicant.domain.Applicant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Optional<Answer> findByApplicant(Applicant applicant);
}
