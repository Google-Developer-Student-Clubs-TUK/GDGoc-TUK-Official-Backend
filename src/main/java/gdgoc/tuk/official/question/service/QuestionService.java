package gdgoc.tuk.official.question.service;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.question.domain.Question;
import gdgoc.tuk.official.question.domain.SubQuestion;
import gdgoc.tuk.official.question.dto.QuestionListResponse;
import gdgoc.tuk.official.question.dto.QuestionResponse;
import gdgoc.tuk.official.question.dto.QuestionUpdateRequest;
import gdgoc.tuk.official.question.exception.DeleteNotAllowedException;
import gdgoc.tuk.official.question.exception.QuestionNotFoundException;
import gdgoc.tuk.official.question.repository.QuestionRepository;
import gdgoc.tuk.official.question.service.mapper.QuestionMapper;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuestionService {

  private final QuestionRepository questionRepository;
  private final QuestionMapper questionMapper;

  public QuestionListResponse findAllQuestionResponses() {
    final List<QuestionResponse> questionResponses =
        questionRepository.findAll().stream()
            .map(q -> new QuestionResponse(q.getId(), q.getContent()))
            .toList();
    return new QuestionListResponse(questionResponses);
  }

  public void updateQuestions(final QuestionUpdateRequest request) {
    createNewQuestion(request);
    updateModifiedQuestion(request);
  }

  private void updateModifiedQuestion(final QuestionUpdateRequest request) {
    request.modifiedQuestions();
  }

  private void createNewQuestion(final QuestionUpdateRequest request) {
    Map<Long, Long> questionOrders = request.questionOrders();
    request.newQuestions().forEach(nq->{
      Long newId = questionRepository.save(questionMapper.toQuestion(nq)).getId();
      questionOrders.put(newId,questionOrders.get(nq.questionId()));
      questionOrders.remove(nq.questionId());
    });
  }

  public void deleteQuestion(final Long questionId) {
    final Question question = getQuestion(questionId);
    if (question.isRequired()) {
      throw new DeleteNotAllowedException(ErrorCode.DELETE_NOT_ALLOWED);
    }
    questionRepository.deleteById(questionId);
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
