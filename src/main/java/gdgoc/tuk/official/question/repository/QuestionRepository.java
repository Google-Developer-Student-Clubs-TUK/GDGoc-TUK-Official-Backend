package gdgoc.tuk.official.question.repository;

import gdgoc.tuk.official.question.domain.Question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("select q from Question q join fetch q.subQuestions where q.id in (:questionIds)")
    List<Question> findAllByIdsFetchSubQuestion(List<Long> questionIds);

    @Query("select q from Question q join fetch q.subQuestions")
    List<Question> findAllFetchSubQuestion();
}
