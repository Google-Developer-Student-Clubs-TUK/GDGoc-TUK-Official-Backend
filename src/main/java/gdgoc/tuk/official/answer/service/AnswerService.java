package gdgoc.tuk.official.answer.service;

import gdgoc.tuk.official.answer.domain.Answer;
import gdgoc.tuk.official.answer.dto.AnswerRequestList;
import gdgoc.tuk.official.answer.exception.DuplicatedAnswerException;
import gdgoc.tuk.official.answer.repository.AnswerRepository;
import gdgoc.tuk.official.applicant.domain.Applicant;
import gdgoc.tuk.official.applicant.service.ApplicantService;
import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.google.service.SpreadSheetsService;
import gdgoc.tuk.official.recruitment.domain.Recruitment;
import gdgoc.tuk.official.recruitment.service.RecruitmentGenerationService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerService {

    private final RecruitmentGenerationService recruitmentGenerationService;
    private final SpreadSheetsService spreadSheetsService;
    private final ApplicantService applicantService;
    private final AnswerRepository answerRepository;

    @Transactional
    public void apply(final AnswerRequestList request) {
        Recruitment recruitment = recruitmentGenerationService.getOnGoingRecruitmentGeneration();
        checkDuplicatedAnswer(request);
        final List<Object> sheetsValues = getSheetsValues(request);
        spreadSheetsService.write(
                recruitment.getSpreadSheetsId(),
                List.of(sheetsValues),
                recruitment.getGeneration());
        Applicant applicant = applicantService.saveApplicant(request.requiredAnswer(),
            recruitment.getGeneration());
        answerRepository.save(new Answer(request.questionAndAnswerJson(),applicant));
    }

    private void checkDuplicatedAnswer(final AnswerRequestList request) {
        boolean alreadyApplied = applicantService.isAlreadyApplied(request.requiredAnswer().email());
        if(alreadyApplied){
            throw new DuplicatedAnswerException(ErrorCode.DUPLICATED_ANSWER);
        }
    }

    private List<Object> getSheetsValues(final AnswerRequestList request) {
        return request.answers().stream().map(a -> (Object) createSheetsAnswer(a)).toList();
    }

    private String createSheetsAnswer(List<String> answers) {
        if (answers.size() == 1) {
            return answers.get(0);
        } else {
            return String.join(",", answers);
        }
    }
}
