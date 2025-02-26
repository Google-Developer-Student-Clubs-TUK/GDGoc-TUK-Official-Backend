package gdgoc.tuk.official.answer.service;

import gdgoc.tuk.official.answer.domain.Answer;
import gdgoc.tuk.official.answer.dto.AnswerRequestList;
import gdgoc.tuk.official.answer.repository.AnswerRepository;
import gdgoc.tuk.official.applicant.service.ApplicantService;
import gdgoc.tuk.official.google.service.SpreadSheetsService;
import gdgoc.tuk.official.recruitment.domain.Recruitment;
import gdgoc.tuk.official.recruitment.service.RecruitmentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerService {

  private final AnswerRepository answerRepository;
  private final RecruitmentService recruitmentService;
  private final SpreadSheetsService spreadSheetsService;
  private final ApplicantService applicantService;

  @Transactional
  public void apply(final AnswerRequestList request) {
    Recruitment recruitment = recruitmentService.getRecruitment();
    final List<Answer> answerList = getAnswerList(request);
    final List<Object> sheetsValues = getSheetsValues(request);
    answerRepository.saveAll(answerList);
    spreadSheetsService.write(
        recruitment.getSpreadSheetsId(), List.of(sheetsValues), recruitment.getGeneration());
  }

  private List<Answer> getAnswerList(final AnswerRequestList request) {
    return request.answerRequests().stream()
        .map(a -> new Answer(a.answer(), a.questionId()))
        .toList();
  }

  private List<Object> getSheetsValues(final AnswerRequestList request) {
    return request.answerRequests().stream().map(a -> (Object) a.answer()).toList();
  }
}
