package gdgoc.tuk.official.question.service;

import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.global.response.IdResponse;
import gdgoc.tuk.official.google.SpreadSheetsService;
import gdgoc.tuk.official.question.domain.Question;
import gdgoc.tuk.official.question.dto.QuestionAddRequest;
import gdgoc.tuk.official.question.dto.QuestionListResponse;
import gdgoc.tuk.official.question.dto.QuestionModifyRequest;
import gdgoc.tuk.official.question.dto.QuestionResponse;
import gdgoc.tuk.official.question.exception.QuestionNotFoundException;
import gdgoc.tuk.official.question.repository.QuestionRepository;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuestionService {

  private final QuestionRepository questionRepository;
  private final SpreadSheetsService spreadSheetsService;

  public QuestionListResponse findAll() {
    final List<QuestionResponse> questionResponses =
        questionRepository.findAll().stream()
            .map(q -> new QuestionResponse(q.getId(), q.getContent()))
            .toList();
    return new QuestionListResponse(questionResponses);
  }

  public void addQuestions(final QuestionAddRequest request) throws IOException {
    final List<Question> questions =
        request.questions().stream().map(q->new Question(q.toString())).toList();
    questionRepository.saveAll(questions);
    spreadSheetsService.createSpreadSheet("5",List.of(request.questions()));
  }

  private List<List<Object>> createSheetValues(final QuestionAddRequest request){
    return List.of(request.questions());
  }

  public void deleteQuestion(final Long questionId) {
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
}
