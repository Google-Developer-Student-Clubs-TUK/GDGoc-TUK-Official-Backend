package gdgoc.tuk.official.answer.service;

import gdgoc.tuk.official.answer.dto.AnswerRequestList;
import gdgoc.tuk.official.answer.repository.AnswerRepository;
import gdgoc.tuk.official.applicant.service.ApplicantService;
import gdgoc.tuk.official.google.service.SpreadSheetsService;
import gdgoc.tuk.official.recruitment.domain.Recruitment;
import gdgoc.tuk.official.recruitment.service.RecruitmentService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
        Recruitment recruitment = recruitmentService.getOnGoingRecruitment();
        final List<Object> sheetsValues = getSheetsValues(request);
        spreadSheetsService.write(
                recruitment.getSpreadSheetsId(),
                List.of(sheetsValues),
                recruitment.getGeneration());
        applicantService.saveApplicant(request.requiredAnswer(), recruitment.getGeneration());
    }

    private List<Object> getSheetsValues(final AnswerRequestList request) {
        return request.answerRequests().stream().map(a -> (Object) getAnswer(a.answer())).toList();
    }

    private String getAnswer(List<String> answers) {
        if (answers.size() == 1) {
            return answers.get(0);
        } else {
            return answers.stream().collect(Collectors.joining(","));
        }
    }
}
