package gdgoc.tuk.official.question.controller;

import gdgoc.tuk.official.question.dto.QuestionListResponse;
import gdgoc.tuk.official.question.dto.QuestionSaveRequestList;
import gdgoc.tuk.official.question.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
@Tag(name = "질문지 API")
public class QuestionController {

  private final QuestionService questionService;

  @GetMapping
  @Operation(summary = "전체 질문 조회", description = "질문 식별자, 질문 내용, 질문 순서를 반환합니다.")
  public QuestionListResponse findAllQuestion() {
    return questionService.findAllQuestionResponses();
  }

  @PostMapping
  @Operation(summary = "질문 생성", description = "질문을 수정/생성 공용 인터페이스입니다.")
  public void addQuestions(@RequestBody QuestionSaveRequestList request) {
    questionService.addQuestions(request);
  }

  @DeleteMapping("/{questionId}")
  @Operation(summary = "질문 삭제", description = "질문 식별자를 이용해 질문을 삭제합니다.")
  public void deleteQuestion(@PathVariable final Long questionId) {
    questionService.deleteQuestion(questionId);
  }
}
