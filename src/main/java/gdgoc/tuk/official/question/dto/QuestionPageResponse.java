package gdgoc.tuk.official.question.dto;

import java.util.List;

public record QuestionPageResponse(
        List<QuestionResponse> questionResponses,
        List<QuestionOrderResponse> questionOrders,
        int lastPage) {}
