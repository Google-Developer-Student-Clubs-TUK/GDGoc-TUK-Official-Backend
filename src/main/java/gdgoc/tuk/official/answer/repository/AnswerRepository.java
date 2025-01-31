package gdgoc.tuk.official.answer.repository;

import gdgoc.tuk.official.answer.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {}
