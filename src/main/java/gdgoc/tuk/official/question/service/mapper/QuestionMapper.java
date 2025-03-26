package gdgoc.tuk.official.question.service.mapper;

import gdgoc.tuk.official.question.domain.Question;
import gdgoc.tuk.official.question.dto.ModifiedQuestion;
import gdgoc.tuk.official.question.dto.ModifiedSubQuestion;
import gdgoc.tuk.official.question.dto.NewQuestion;
import gdgoc.tuk.official.question.dto.QuestionOrderResponse;
import gdgoc.tuk.official.question.dto.QuestionResponse;
import gdgoc.tuk.official.question.dto.SubQuestionResponse;
import gdgoc.tuk.official.questionorder.domain.QuestionOrders;

import org.springframework.stereotype.Component;

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

    public void modifyQuestion(final Question question, final ModifiedQuestion modifiedQuestion) {
        Map<Long, String> subQuestionMap = new HashMap<>();
        if (Objects.nonNull(modifiedQuestion.modifiedSubQuestions())) {
            subQuestionMap =
                    modifiedQuestion.modifiedSubQuestions().stream()
                            .collect(
                                    Collectors.toMap(
                                            ModifiedSubQuestion::subQuestionId,
                                            ModifiedSubQuestion::modifiedSubContent));
        }
        question.modifyContent(
                modifiedQuestion.modifiedContent(),
                modifiedQuestion.questionType(),
                modifiedQuestion.isRequired(),
                subQuestionMap);
    }

    public QuestionResponse toQuestionResponse(final Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .questionType(question.getQuestionType())
                .content(question.getContent())
                .isRequired(question.isRequired())
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
