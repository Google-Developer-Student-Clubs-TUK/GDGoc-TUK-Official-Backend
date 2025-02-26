package gdgoc.tuk.official.question.dto;

public record QuestionSaveRequest(Long questionId, Integer order, boolean isRequired, String content) {}
