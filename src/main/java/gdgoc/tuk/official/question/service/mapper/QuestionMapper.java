package gdgoc.tuk.official.question.service.mapper;

import gdgoc.tuk.official.question.domain.Question;
import gdgoc.tuk.official.question.dto.NewQuestion;

public class QuestionMapper {

    public Question toQuestion(final NewQuestion newQuestion){
    return Question.builder()
        .questionType(newQuestion.questionType())
        .content(newQuestion.content())
        .isRequired(newQuestion.isRequired())
        .build();
    }
}
