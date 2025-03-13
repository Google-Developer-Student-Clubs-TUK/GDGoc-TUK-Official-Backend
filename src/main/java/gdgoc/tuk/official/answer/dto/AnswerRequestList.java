package gdgoc.tuk.official.answer.dto;

import java.util.List;

public record AnswerRequestList(
        RequiredAnswer requiredAnswer, List<AnswerRequest> answerRequests, String generation) {}
