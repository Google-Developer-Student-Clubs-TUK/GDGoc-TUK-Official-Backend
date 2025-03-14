package gdgoc.tuk.official.question.service;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.question.domain.Question;
import gdgoc.tuk.official.question.domain.SubQuestion;
import gdgoc.tuk.official.question.dto.ModifiedQuestion;
import gdgoc.tuk.official.question.dto.QuestionDeleteRequest;
import gdgoc.tuk.official.question.dto.QuestionListResponse;
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
import gdgoc.tuk.official.recruitment.service.RecruitmentGenerationService;

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
    private final RecruitmentGenerationService recruitmentGenerationService;

    public QuestionListResponse findAllQuestionResponses() {
        final List<QuestionResponse> questionResponses =
                questionRepository.findAllFetchSubQuestion().stream()
                        .map(questionMapper::toQuestionResponse)
                        .toList();
        final List<QuestionOrderResponse> questionOrderResponseList =
                questionMapper.toQuestionOrderResponseList(questionOrderRepository.findAll());
        String generation = recruitmentGenerationService.getOnGoingRecruitmentGeneration().getGeneration();
        return new QuestionListResponse(questionResponses, questionOrderResponseList, generation);
    }

    @Transactional
    public void updateQuestions(final QuestionUpdateRequest request) {
        checkAlreadyOpened();
        Map<Long, Integer> newOrderMap = createNewQuestion(request);
        updateModifiedQuestion(request);
        if (!newOrderMap.isEmpty()) {
            updateQuestionOrder(newOrderMap);
        }
    }

    private void checkAlreadyOpened() {
        if (recruitmentRepository.existsByCloseAtIsAfter(LocalDateTime.now())) {
            throw new QuestionModifyNotAllowed(ErrorCode.MODIFY_NOT_ALLOWED);
        }
    }

    private void updateQuestionOrder(final Map<Long, Integer> orderMap) {
        List<QuestionOrders> questionOrders = questionOrderRepository.findAll();
        questionOrders.forEach(qo -> qo.changeOrder(orderMap.get(qo.getQuestionId())));
    }

    private void updateModifiedQuestion(final QuestionUpdateRequest request) {
        if (request.modifiedQuestions().isEmpty()) return;
        final List<ModifiedQuestion> modifiedQuestions = request.modifiedQuestions();
        final Map<Long, ModifiedQuestion> modifiedQuestionMap =
                modifiedQuestions.stream()
                        .collect(Collectors.toMap(ModifiedQuestion::questionId, q -> q));
        final List<Long> modifiedQuestionIds =
                modifiedQuestions.stream().map(ModifiedQuestion::questionId).toList();
        final List<Question> questions =
                questionRepository.findAllByIdsFetchSubQuestion(modifiedQuestionIds);
        questions.forEach(
                q -> {
                    ModifiedQuestion modifiedQuestion = modifiedQuestionMap.get(q.getId());
                    questionMapper.modifyQuestion(q, modifiedQuestion);
                });
    }

    private Map<Long, Integer> createNewQuestion(final QuestionUpdateRequest request) {
        final Map<Long, Integer> questionOrderMap =
                request.updatedQuestionOrders().stream()
                        .collect(
                                Collectors.toMap(
                                        UpdatedQuestionOrder::getQuestionId,
                                        UpdatedQuestionOrder::getOrder));
        if (request.newQuestions().isEmpty()) return questionOrderMap;
        request.newQuestions()
                .forEach(
                        nq -> {
                            Question newQuestion =
                                    questionRepository.save(questionMapper.toQuestion(nq));
                            questionOrderRepository.save(
                                    new QuestionOrders(
                                            newQuestion.getId(),
                                            questionOrderMap.get(nq.questionId())));
                            questionOrderMap.put(
                                    newQuestion.getId(), questionOrderMap.get(nq.questionId()));
                            questionOrderMap.remove(nq.questionId());
                            nq.newSubQuestions()
                                    .forEach(
                                            sq ->
                                                    newQuestion.addSubQuestion(
                                                            new SubQuestion(sq.newSubContent())));
                        });
        return questionOrderMap;
    }

    @Transactional
    public void deleteQuestion(final Long questionId, final QuestionDeleteRequest request) {
        final Question question = getQuestion(questionId);
        if (question.isRequired()) {
            throw new DeleteNotAllowedException(ErrorCode.DELETE_NOT_ALLOWED);
        }
        questionRepository.deleteById(questionId);
        questionOrderRepository.deleteByQuestionId(questionId);
        reorder(request.questionOrders());
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

    public List<Question> findAllQuestions() {
        return questionRepository.findAll();
    }
}
