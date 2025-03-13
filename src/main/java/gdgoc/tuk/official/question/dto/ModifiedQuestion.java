package gdgoc.tuk.official.question.dto;

import gdgoc.tuk.official.question.domain.QuestionType;
import java.util.List;

public record ModifiedQuestion(
        Long questionId,
        String modifiedContent,
        QuestionType questionType,
        boolean isRequired,
        List<ModifiedSubQuestion> modifiedSubQuestions) {}
