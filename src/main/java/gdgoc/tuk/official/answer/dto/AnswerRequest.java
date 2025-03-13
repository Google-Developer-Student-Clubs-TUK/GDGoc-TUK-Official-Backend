package gdgoc.tuk.official.answer.dto;

import java.util.List;

public record AnswerRequest(
    Long questionId,
    List<String> answer
) {}
