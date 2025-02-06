package gdgoc.tuk.official.answer.dto;

public record AnswerRequest(
    Long questionId,
    String answer
) {}
