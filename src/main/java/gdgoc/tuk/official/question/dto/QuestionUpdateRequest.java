package gdgoc.tuk.official.question.dto;

import java.util.List;

public record QuestionUpdateRequest(
    List<ModifiedQuestion> modifiedQuestions,
    List<NewQuestion> newQuestions,
    List<UpdatedQuestionOrder> updatedQuestionOrders
) {}
