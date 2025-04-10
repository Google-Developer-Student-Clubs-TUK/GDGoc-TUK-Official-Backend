package gdgoc.tuk.official.question.dto;

import gdgoc.tuk.official.question.domain.QuestionType;
import java.util.List;
import lombok.Builder;

@Builder
public record QuestionResponse(
        Long questionId,
        String content,
        List<SubQuestionResponse> subQuestions,
        int page,
        boolean isRequired,
        boolean isDeletable,
        Integer order,
        QuestionType questionType) {}
