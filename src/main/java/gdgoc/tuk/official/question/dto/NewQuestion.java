package gdgoc.tuk.official.question.dto;

import gdgoc.tuk.official.question.domain.QuestionType;

import java.util.List;

public record NewQuestion(
        Long questionId,
        String content,
        QuestionType questionType,
        int page,
        boolean isRequired,
        boolean isDeletable,
        List<NewSubQuestion> newSubQuestions) {}
