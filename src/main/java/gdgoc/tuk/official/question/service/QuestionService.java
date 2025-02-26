package gdgoc.tuk.official.question.service;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.global.response.IdResponse;
import gdgoc.tuk.official.question.domain.Question;
import gdgoc.tuk.official.question.dto.QuestionListResponse;
import gdgoc.tuk.official.question.dto.QuestionModifyRequest;
import gdgoc.tuk.official.question.dto.QuestionResponse;
import gdgoc.tuk.official.question.dto.QuestionSaveRequestList;
import gdgoc.tuk.official.question.exception.DeleteNotAllowedException;
import gdgoc.tuk.official.question.exception.QuestionNotFoundException;
import gdgoc.tuk.official.question.repository.QuestionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuestionService {

  private final QuestionRepository questionRepository;

  public QuestionListResponse findAllQuestionResponses() {
    final List<QuestionResponse> questionResponses =
        questionRepository.findAll().stream()
            .map(q -> new QuestionResponse(q.getId(), q.getContent()))
            .toList();
    return new QuestionListResponse(questionResponses);
  }

  public void addQuestions(final QuestionSaveRequestList request) {
    final List<Question> questions =
        request.questionSaveRequests().stream()
            .map(Question::new)
            .toList();
    questionRepository.saveAll(questions);
  }

  public void deleteQuestion(final Long questionId) {
    final Question question = getQuestion(questionId);
    if(question.isRequired()){
      throw new DeleteNotAllowedException(ErrorCode.DELETE_NOT_ALLOWED);
    }
    questionRepository.deleteById(questionId);
  }

  public IdResponse modifyQuestion(final Long questionId, final QuestionModifyRequest request) {
    final Question question = getQuestion(questionId);
    question.modify(request.modifiedContent());
    return new IdResponse(questionId);
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
