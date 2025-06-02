package gdgoc.tuk.official.question.service;

import static com.mysema.commons.lang.Assert.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import gdgoc.tuk.official.config.TestConfig;
import gdgoc.tuk.official.question.domain.Question;
import gdgoc.tuk.official.question.domain.QuestionType;
import gdgoc.tuk.official.question.dto.QuestionDeleteRequest;
import gdgoc.tuk.official.question.dto.UpdatedQuestionOrder;
import gdgoc.tuk.official.question.exception.DeleteNotAllowedException;
import gdgoc.tuk.official.question.repository.QuestionRepository;
import gdgoc.tuk.official.question.service.mapper.QuestionMapper;
import gdgoc.tuk.official.questionorder.domain.QuestionOrders;
import gdgoc.tuk.official.questionorder.repository.QuestionOrderRepository;
import gdgoc.tuk.official.recruitment.domain.Recruitment;
import gdgoc.tuk.official.recruitment.repository.RecruitmentRepository;
import gdgoc.tuk.official.recruitment.service.RecruitmentValidator;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Import(TestConfig.class)
@Transactional
class QuestionServiceTest {

    @Autowired private QuestionRepository questionRepository;
    @Autowired private QuestionMapper questionMapper;
    @Autowired private QuestionOrderRepository questionOrderRepository;
    @Autowired private RecruitmentValidator recruitmentValidator;
    @Autowired private QuestionService questionService;
    @Autowired private RecruitmentRepository recruitmentRepository;

    void setUpQuestion(){
        LocalDateTime now = LocalDateTime.now();
        String generation = String.valueOf(now.getYear());
        recruitmentRepository.save(new Recruitment(generation,"url","id",now,now.plusDays(4)));

        Question question1 = new Question("질문1", QuestionType.SHORT_TEXT, 0, true, true);
        Question question2 = new Question("질문2", QuestionType.SHORT_TEXT, 0, true, true);
        Question question3 = new Question("질문3", QuestionType.SHORT_TEXT, 0, true, true);
        questionRepository.saveAll(List.of(question1,question2,question3));

        QuestionOrders questionOrders1 = new QuestionOrders(question1.getId(), 0);
        QuestionOrders questionOrders2 = new QuestionOrders(question2.getId(), 0);
        QuestionOrders questionOrders3 = new QuestionOrders(question3.getId(), 0);
        questionOrderRepository.saveAll(List.of(questionOrders1,questionOrders2,questionOrders3));
    }

    @Test
    @DisplayName("Deletable이 False인 질문은 삭제할 경우 예외가 발생한다.")
    void cannotDeleteBasicQuestion() {
        // given
        Question question = new Question("질문1", QuestionType.SHORT_TEXT, 0, true, false);
        questionRepository.save(question);
        QuestionOrders questionOrders = new QuestionOrders(question.getId(), 0);
        questionOrderRepository.save(questionOrders);
        new QuestionDeleteRequest(question.getId(), List.of(new UpdatedQuestionOrder(question.getId(),
            questionOrders.getOrders())));
        // when & then
        assertThatThrownBy(() -> questionService.deleteQuestion(
            question.getId(),
            new QuestionDeleteRequest(question.getId(), List.of(new UpdatedQuestionOrder(question.getId(),
                questionOrders.getOrders())))))
            .isInstanceOf(DeleteNotAllowedException.class);
    }

    @Test
    @DisplayName("중간 순서의 질문을 제거할 경우 순서가 재배열된다.")
    void reorderIfDeleteQuestion() {
        // given
        Question question1 = new Question("질문1", QuestionType.SHORT_TEXT, 0, true, true);
        Question question2 = new Question("질문2", QuestionType.SHORT_TEXT, 0, true, true);
        Question question3 = new Question("질문3", QuestionType.SHORT_TEXT, 0, true, true);
        questionRepository.saveAll(List.of(question1,question2,question3));

        QuestionOrders questionOrders1 = new QuestionOrders(question1.getId(), 0);
        QuestionOrders questionOrders2 = new QuestionOrders(question2.getId(), 1);
        QuestionOrders questionOrders3 = new QuestionOrders(question3.getId(), 2);
        questionOrderRepository.saveAll(List.of(questionOrders1,questionOrders2,questionOrders3));

        // when
        questionService.deleteQuestion(
                question2.getId(),
                new QuestionDeleteRequest(
                        question2.getId(),
                        List.of(
                                new UpdatedQuestionOrder(
                                        question1.getId(), questionOrders1.getOrders()),
                                new UpdatedQuestionOrder(question3.getId(),questionOrders3.getOrders()-1))));

        // then
        QuestionOrders questionOrders = questionOrderRepository.findById(questionOrders3.getId()).get();
        Assertions.assertThat(questionOrders.getOrders()).isEqualTo(1);
    }
}
