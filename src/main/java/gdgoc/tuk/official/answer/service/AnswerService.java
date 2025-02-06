package gdgoc.tuk.official.answer.service;

import gdgoc.tuk.official.answer.domain.Answer;
import gdgoc.tuk.official.answer.dto.AnswerRequestList;
import gdgoc.tuk.official.answer.repository.AnswerRepository;
import gdgoc.tuk.official.google.service.SpreadSheetsService;
import gdgoc.tuk.official.recruitment.domain.Recruitment;
import gdgoc.tuk.official.recruitment.service.RecruitmentService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerService {

  private static final String POSITION = "시트1!%d:%d";
  private final AnswerRepository answerRepository;
  private final RecruitmentService recruitmentService;
  private final SpreadSheetsService spreadSheetsService;

  @Transactional
  public void apply(final AnswerRequestList request){
    Recruitment recruitment = recruitmentService.getRecruitment();
    final List<Answer> answerList = request.answerRequests().stream()
        .map(a -> new Answer(a.answer(), a.questionId())).toList();
    final List<Object> values = request.answerRequests().stream()
        .map(a -> (Object) a.answer()).toList();
    answerRepository.saveAll(answerList);
    spreadSheetsService.write(recruitment.getSpreadSheetsId(),List.of(values),
        POSITION.formatted(recruitment.getEmptyRowNumber(),recruitment.getEmptyRowNumber()));
    recruitment.increaseRowNumber();
  }
}
