package gdgoc.tuk.official.answer.dto;

import java.util.List;

public record AnswerRequestList(
        RequiredAnswer requiredAnswer, List<List<String>> answers,
        String questionAndAnswerJson) {}
