package gdgoc.tuk.official.recruitment.service;

import gdgoc.tuk.official.answer.repository.NextAnswerRowRedisRepository;
import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.google.initializer.SpreadSheetsInitializer;
import gdgoc.tuk.official.question.service.QuestionService;
import gdgoc.tuk.official.recruitment.domain.Recruitment;
import gdgoc.tuk.official.recruitment.dto.RecruitmentOpenRequest;
import gdgoc.tuk.official.recruitment.exception.GenerationDuplicationException;
import gdgoc.tuk.official.recruitment.exception.RecruitmentDuplicationException;
import gdgoc.tuk.official.recruitment.exception.RecruitmentNotExistException;
import gdgoc.tuk.official.recruitment.repository.RecruitmentRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitmentService {

  private final RecruitmentRepository recruitmentRepository;
  private final QuestionService questionService;
  private final SpreadSheetsInitializer spreadSheetsInitializer;
  private final NextAnswerRowRedisRepository nextAnswerRowRedisRepository;

  @Transactional
  public void openRecruitment(final RecruitmentOpenRequest request) {
    checkGeneration(request.generation());
    checkAlreadyExistRecruitment();
    nextAnswerRowRedisRepository.saveNewGeneration(request.generation());
    String spreadSheetsId =
        spreadSheetsInitializer.init(request.generation(), getSpreadSheetQuestions());
    final Recruitment recruitment =
        new Recruitment(request.generation(), spreadSheetsId, request.openAt(), request.closeAt());
    recruitmentRepository.save(recruitment);
  }

  private List<List<Object>> getSpreadSheetQuestions() {
    return List.of(
        questionService.findAllQuestions().stream().map(q -> (Object) q.getContent()).toList());
  }

  private void checkAlreadyExistRecruitment() {
    LocalDateTime currentTime = LocalDateTime.now();
    if (recruitmentRepository.existsByCloseAtIsAfter(currentTime)) {
      throw new RecruitmentDuplicationException(ErrorCode.RECRUITMENT_ALREADY_EXIST);
    }
  }

  private void checkGeneration(final String generation) {
    if (recruitmentRepository.existsByGeneration(generation)) {
      throw new GenerationDuplicationException(ErrorCode.GENERATION_DUPLICATED);
    }
  }

  public Recruitment getRecruitment() {
    return recruitmentRepository
            .findByBetweenOpenAtAndCloseAt(LocalDateTime.now())
            .orElseThrow(() -> new RecruitmentNotExistException(ErrorCode.RECRUITMENT_NOT_FOUND));
  }
}
