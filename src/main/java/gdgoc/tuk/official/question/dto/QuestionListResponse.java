package gdgoc.tuk.official.question.dto;

import java.util.List;

public record QuestionListResponse(
        List<QuestionResponse> questionResponses,
        List<QuestionOrderResponse> questionOrders,
        String generation) {}
