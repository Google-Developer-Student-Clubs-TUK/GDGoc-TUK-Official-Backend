package gdgoc.tuk.official.google.service;

import gdgoc.tuk.official.question.domain.Question;
import gdgoc.tuk.official.question.service.QuestionService;
import gdgoc.tuk.official.questionorder.domain.QuestionOrders;
import gdgoc.tuk.official.questionorder.service.QuestionOrderService;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpreadSheetsQuestionService {

    private final QuestionService questionService;
    private final QuestionOrderService questionOrderService;

    public List<List<Object>> getSpreadSheetQuestions() {
        Map<Long, Integer> orderMap =
            questionOrderService.findAllQuestionOrders().stream()
                .collect(
                    Collectors.toMap(
                        QuestionOrders::getQuestionId, QuestionOrders::getOrders));
        List<Question> sortedQuestions =
            questionService.findAllQuestions().stream()
                .sorted(
                    Comparator.comparing(
                        q -> orderMap.getOrDefault(q.getId(), Integer.MAX_VALUE)))
                .toList();
        return List.of(sortedQuestions.stream().map(q -> (Object) q.getContent()).toList());
    }
}
