package gdgoc.tuk.official.question.dto;

import java.util.List;

public record QuestionDeleteRequest(Long questionId, List<UpdatedQuestionOrder> questionOrders) {}
