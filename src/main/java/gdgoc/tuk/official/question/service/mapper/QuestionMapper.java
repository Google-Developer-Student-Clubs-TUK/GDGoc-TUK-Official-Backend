package gdgoc.tuk.official.question.service.mapper;

import gdgoc.tuk.official.question.domain.Question;
import gdgoc.tuk.official.question.dto.ModifiedQuestion;
import gdgoc.tuk.official.question.dto.ModifiedSubQuestion;
import gdgoc.tuk.official.question.dto.NewQuestion;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {

  public Question toQuestion(final NewQuestion newQuestion) {
    return Question.builder()
        .questionType(newQuestion.questionType())
        .content(newQuestion.content())
        .isRequired(newQuestion.isRequired())
        .build();
  }

  public void modifyQuestion(final Question question, final ModifiedQuestion modifiedQuestion) {
    Map<Long, String> subQuestionMap = modifiedQuestion.modifiedSubQuestions().stream().collect(
        Collectors.toMap(ModifiedSubQuestion::subQuestionId,
            ModifiedSubQuestion::modifiedSubContent));
    question.modifyContent(
        modifiedQuestion.modifiedContent(),
        modifiedQuestion.questionType(),
        modifiedQuestion.isRequired(), subQuestionMap);

  }
}
