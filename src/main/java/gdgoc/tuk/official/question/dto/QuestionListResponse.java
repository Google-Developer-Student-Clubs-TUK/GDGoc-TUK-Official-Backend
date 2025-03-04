package gdgoc.tuk.official.question.dto;

import gdgoc.tuk.official.questionorder.domain.QuestionOrders;
import java.util.List;

public record QuestionListResponse(List<QuestionResponse> questionResponses,
                                   List<QuestionOrderResponse>questionOrders) {}
