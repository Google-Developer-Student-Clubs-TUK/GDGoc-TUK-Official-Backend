package gdgoc.tuk.official.question.service.mapper;

import gdgoc.tuk.official.question.domain.Question;
import gdgoc.tuk.official.question.dto.ModifiedQuestion;
import gdgoc.tuk.official.question.dto.ModifiedSubQuestion;
import gdgoc.tuk.official.question.dto.NewQuestion;
import gdgoc.tuk.official.question.dto.NewSubQuestion;
import gdgoc.tuk.official.question.dto.QuestionOrderResponse;
import gdgoc.tuk.official.question.dto.QuestionResponse;
import gdgoc.tuk.official.question.dto.SubQuestionResponse;
import gdgoc.tuk.official.questionorder.domain.QuestionOrders;

import java.util.ArrayList;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class QuestionMapper {

    public Question toQuestion(final NewQuestion newQuestion) {
        return Question.builder()
                .questionType(newQuestion.questionType())
                .content(newQuestion.content())
                .isRequired(newQuestion.isRequired())
                .deletable(newQuestion.isDeletable())
                .build();
    }

    private Map<Long, Integer> toQuestionOrderMap(final List<QuestionOrders> questionOrders) {
        return questionOrders.stream()
                .collect(
                        Collectors.toMap(QuestionOrders::getQuestionId, QuestionOrders::getOrders));
    }

    public List<QuestionResponse> toSortedQuestionResponseList(
            List<Question> questions, final List<QuestionOrders> questionOrders) {
        final Map<Long, Integer> questionOrdersMap = toQuestionOrderMap(questionOrders);
        final List<QuestionResponse> questionResponses =
                questions.stream()
                        .map(q -> toQuestionResponse(q, questionOrdersMap.get(q.getId())))
                        .collect(Collectors.toList());
        questionResponses.sort(Comparator.comparing(QuestionResponse::order));
        return questionResponses;
    }

    public void modifyQuestion(final Question question, final ModifiedQuestion modifiedQuestion) {
        Map<Long, String> modifiedSubQuestionMap = new HashMap<>();
        List<String> newSubQuestionList = new ArrayList<>();
        if (Objects.nonNull(modifiedQuestion.newSubQuestions())) {
            newSubQuestionList = getSubContent(modifiedQuestion);
        }
        if (Objects.nonNull(modifiedQuestion.modifiedSubQuestions())) {
            modifiedSubQuestionMap = getModifiedSubQuestionMap(modifiedQuestion);
        }
        question.modifyContent(
                modifiedQuestion.modifiedContent(),
                modifiedQuestion.questionType(),
                modifiedQuestion.isRequired(),
                modifiedSubQuestionMap,
                newSubQuestionList);
    }

    private Map<Long, String> getModifiedSubQuestionMap(final ModifiedQuestion modifiedQuestion) {
        Map<Long, String> modifiedSubQuestionMap;
        modifiedSubQuestionMap =
                modifiedQuestion.modifiedSubQuestions().stream()
                        .collect(
                                Collectors.toMap(
                                        ModifiedSubQuestion::subQuestionId,
                                        ModifiedSubQuestion::modifiedSubContent));
        return modifiedSubQuestionMap;
    }

    private List<String> getSubContent(final ModifiedQuestion modifiedQuestion) {
        List<String> newSubQuestionList;
        newSubQuestionList =
                modifiedQuestion.newSubQuestions().stream()
                        .map(NewSubQuestion::newSubContent)
                        .toList();
        return newSubQuestionList;
    }

    public QuestionResponse toQuestionResponse(
            final Question question, final Integer questionOrder) {
        return QuestionResponse.builder()
                .questionId(question.getId())
                .questionType(question.getQuestionType())
                .content(question.getContent())
                .isRequired(question.isRequired())
                .isDeletable(question.isDeletable())
                .order(questionOrder)
                .subQuestions(
                        question.getSubQuestions().stream()
                                .map(sq -> new SubQuestionResponse(sq.getId(), sq.getSubContent()))
                                .toList())
                .build();
    }

    public List<QuestionOrderResponse> toQuestionOrderResponseList(
            final List<QuestionOrders> questionOrders) {
        return questionOrders.stream()
                .map(qo -> new QuestionOrderResponse(qo.getQuestionId(), qo.getOrders()))
                .toList();
    }
}
