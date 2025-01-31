package gdgoc.tuk.official.question.controller;

import gdgoc.tuk.official.global.response.IdResponse;
import gdgoc.tuk.official.question.dto.QuestionSaveRequestList;
import gdgoc.tuk.official.question.dto.QuestionListResponse;
import gdgoc.tuk.official.question.dto.QuestionModifyRequest;
import gdgoc.tuk.official.question.service.QuestionService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {

  private final QuestionService questionService;

  @GetMapping
  public QuestionListResponse findAllQuestion() {
    return questionService.findAllQuestionResponses();
  }

  @PostMapping
  public void addQuestions(@RequestBody QuestionSaveRequestList request) throws IOException {
    questionService.addQuestions(request);
  }

  @DeleteMapping("/{questionId}")
  public void deleteQuestion(@PathVariable final Long questionId) {
    questionService.deleteQuestion(questionId);
  }

  @PatchMapping("/{questionId}")
  public IdResponse modifyQuestion(
      @PathVariable final Long questionId, @RequestBody final QuestionModifyRequest request) {
    return questionService.modifyQuestion(questionId, request);
  }
}
