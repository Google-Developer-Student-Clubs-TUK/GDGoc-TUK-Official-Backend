package gdgoc.tuk.official.question.dto;

import java.util.List;
import java.util.Map;

public record QuestionUpdateRequest(
    List<ModifiedQuestion> modifiedQuestions,
    List<NewQuestion> newQuestions,
    Map<Long,Long> questionOrders
) {}
