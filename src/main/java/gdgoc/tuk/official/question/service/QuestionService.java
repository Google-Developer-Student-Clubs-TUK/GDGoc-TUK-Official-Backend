package gdgoc.tuk.official.question.service;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.question.domain.Question;
import gdgoc.tuk.official.question.domain.SubQuestion;
import gdgoc.tuk.official.question.dto.ModifiedQuestion;
import gdgoc.tuk.official.question.dto.NewQuestion;
import gdgoc.tuk.official.question.dto.QuestionDeleteRequest;
import gdgoc.tuk.official.question.dto.QuestionPageResponse;
import gdgoc.tuk.official.question.dto.QuestionOrderResponse;
import gdgoc.tuk.official.question.dto.QuestionResponse;
import gdgoc.tuk.official.question.dto.QuestionUpdateRequest;
import gdgoc.tuk.official.question.dto.UpdatedQuestionOrder;
import gdgoc.tuk.official.question.exception.DeleteNotAllowedException;
import gdgoc.tuk.official.question.exception.QuestionModifyNotAllowed;
import gdgoc.tuk.official.question.exception.QuestionNotFoundException;
import gdgoc.tuk.official.question.repository.QuestionRepository;
import gdgoc.tuk.official.question.service.mapper.QuestionMapper;
import gdgoc.tuk.official.questionorder.domain.QuestionOrders;
import gdgoc.tuk.official.questionorder.repository.QuestionOrderRepository;
import gdgoc.tuk.official.recruitment.repository.RecruitmentRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final QuestionOrderRepository questionOrderRepository;
    private final RecruitmentRepository recruitmentRepository;

    public QuestionPageResponse findAllQuestionsAndSubQuestionsWithOrder() {
        List<Question> questions = questionRepository.findAllFetchSubQuestion();
        final List<QuestionOrders> questionOrders = questionOrderRepository.findAll();
        final List<QuestionResponse> questionsSortedByOrder =
                questionMapper.toSortedQuestionResponseList(questions, questionOrders);
        final List<QuestionOrderResponse> questionOrderResponses =
                questionMapper.toQuestionOrderResponseList(questionOrders);
        int lastPage = findLastPage(questionsSortedByOrder);
        return new QuestionPageResponse(questionsSortedByOrder, questionOrderResponses,lastPage);
    }

    private int findLastPage(final List<QuestionResponse> questionsSortedByOrder) {
        int lastIndex = questionsSortedByOrder.size() - 1;
        QuestionResponse questionResponse = questionsSortedByOrder.get(lastIndex);
        return questionResponse.page();
    }

    @Transactional
    public void saveAndModifyQuestions(final QuestionUpdateRequest request) {
        checkModifiable();
        Map<Long, Integer> newOrderMap = saveQuestions(request);
        modifyQuestion(request);
        if (!newOrderMap.isEmpty()) {
            updateQuestionOrder(newOrderMap);
        }
    }

    private void checkModifiable() {
        if (recruitmentRepository.existsByCloseAtIsAfter(LocalDateTime.now())) {
            throw new QuestionModifyNotAllowed(ErrorCode.MODIFY_NOT_ALLOWED);
        }
    }

    private void updateQuestionOrder(final Map<Long, Integer> orderMap) {
        List<QuestionOrders> questionOrders = questionOrderRepository.findAll();
        questionOrders.forEach(qo -> qo.changeOrder(orderMap.get(qo.getQuestionId())));
    }

    private void modifyQuestion(final QuestionUpdateRequest request) {
        if (request.modifiedQuestions().isEmpty()) return;
        final Map<Long, ModifiedQuestion> modifiedQuestionMap =
                getModifiedQuestionMap(request);
        final List<Question> questions = findAllQuestionsByIds(request.modifiedQuestions());
        questions.forEach(
                q -> {
                    ModifiedQuestion modifiedQuestion = modifiedQuestionMap.get(q.getId());
                    questionMapper.modifyQuestion(q, modifiedQuestion);
                });
    }

    private Map<Long, ModifiedQuestion> getModifiedQuestionMap(
            final QuestionUpdateRequest request) {
        return request.modifiedQuestions().stream()
                .collect(Collectors.toMap(ModifiedQuestion::questionId, q -> q));
    }

    private List<Question> findAllQuestionsByIds(final List<ModifiedQuestion> modifiedQuestions) {
        final List<Long> modifiedQuestionIds =
                modifiedQuestions.stream().map(ModifiedQuestion::questionId).toList();
        return questionRepository.findAllByIdsFetchSubQuestion(modifiedQuestionIds);
    }

    private Map<Long, Integer> saveQuestions(final QuestionUpdateRequest request) {
        final Map<Long, Integer> newQuestionOrder = getNewQuestionOrderMap(request);
        if (request.newQuestions().isEmpty()) return newQuestionOrder;
        request.newQuestions()
                .forEach(nq -> saveQuestionsAndSubQuestionsAndOrder(nq, newQuestionOrder));
        return newQuestionOrder;
    }

    private Map<Long, Integer> getNewQuestionOrderMap(final QuestionUpdateRequest request) {
        return request.updatedQuestionOrders().stream()
                .collect(
                        Collectors.toMap(
                                UpdatedQuestionOrder::getQuestionId,
                                UpdatedQuestionOrder::getOrder));
    }

    private void saveQuestionsAndSubQuestionsAndOrder(
            final NewQuestion nq, final Map<Long, Integer> newQuestionOrder) {
        Question newQuestion = questionRepository.save(questionMapper.toQuestion(nq));
        questionOrderRepository.save(
                new QuestionOrders(newQuestion.getId(), newQuestionOrder.get(nq.questionId())));
        newQuestionOrder.put(newQuestion.getId(), newQuestionOrder.get(nq.questionId()));
        newQuestionOrder.remove(nq.questionId());
        nq.newSubQuestions()
                .forEach(sq -> newQuestion.addSubQuestion(new SubQuestion(sq.newSubContent())));
    }

    @Transactional
    public void deleteQuestion(final Long questionId, final QuestionDeleteRequest request) {
        final Question question = getQuestion(questionId);
        if (question.isNotDeletable()) {
            throw new DeleteNotAllowedException(ErrorCode.DELETE_NOT_ALLOWED);
        }
        questionRepository.deleteById(questionId);
        questionOrderRepository.deleteByQuestionId(questionId);
        reorder(request.questionOrders());
    }

    @Transactional
    public void deleteSubQuestion(final Long questionId,final Long subQuestionId) {
        final Question question = getQuestionFetchSubQuestions(questionId);
        if (question.isNotDeletable()) {
            throw new DeleteNotAllowedException(ErrorCode.DELETE_NOT_ALLOWED);
        }
        question.deleteBySubQuestionId(subQuestionId);
    }

    private void reorder(final List<UpdatedQuestionOrder> questionOrders) {
        Map<Long, Integer> questionOrderMap =
                questionOrders.stream()
                        .collect(
                                Collectors.toMap(
                                        UpdatedQuestionOrder::getQuestionId,
                                        UpdatedQuestionOrder::getOrder));
        questionOrderRepository
                .findAll()
                .forEach(qo -> qo.changeOrder(questionOrderMap.get(qo.getQuestionId())));
    }

    public Question getQuestion(final Long questionId) {
        return questionRepository
                .findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(ErrorCode.QUESTION_NOT_FOUND));
    }

    public Question getQuestionFetchSubQuestions(final Long questionId) {
        return questionRepository
            .findByIdFetchSubQuestions(questionId)
            .orElseThrow(() -> new QuestionNotFoundException(ErrorCode.QUESTION_NOT_FOUND));
    }

    public List<Question> findAllQuestions() {
        return questionRepository.findAll();
    }
}
