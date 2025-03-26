package gdgoc.tuk.official.question.dto;

import gdgoc.tuk.official.question.domain.QuestionType;
import gdgoc.tuk.official.question.domain.SubQuestion;
import java.util.List;
import lombok.Builder;

@Builder
public record QuestionResponse(
        Long id,
        String content,
        List<SubQuestionResponse> subQuestions,
        boolean isRequired,
        boolean isDeletable,
        Integer order,
        QuestionType questionType) {}
