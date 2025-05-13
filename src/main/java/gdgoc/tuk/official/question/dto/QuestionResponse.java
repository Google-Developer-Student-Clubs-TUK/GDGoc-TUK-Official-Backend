package gdgoc.tuk.official.question.dto;

import gdgoc.tuk.official.question.domain.QuestionType;

import lombok.Builder;

import java.util.List;

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
