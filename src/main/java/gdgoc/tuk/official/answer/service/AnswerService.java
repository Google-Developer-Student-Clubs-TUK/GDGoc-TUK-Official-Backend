package gdgoc.tuk.official.answer.service;

import gdgoc.tuk.official.answer.domain.Answer;
import gdgoc.tuk.official.answer.dto.AnswerListRequest;
import gdgoc.tuk.official.answer.dto.AnswerResponse;
import gdgoc.tuk.official.answer.exception.AnswerNotFoundException;
import gdgoc.tuk.official.answer.exception.DuplicatedAnswerException;
import gdgoc.tuk.official.answer.repository.AnswerRepository;
import gdgoc.tuk.official.applicant.domain.Applicant;
import gdgoc.tuk.official.applicant.service.ApplicantService;
import gdgoc.tuk.official.applicant.service.ApplicantValidator;
import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.google.service.SpreadSheetsService;
import gdgoc.tuk.official.recruitment.domain.Recruitment;
import gdgoc.tuk.official.recruitment.service.RecruitmentService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerService {

    private final SpreadSheetsService spreadSheetsService;
    private final ApplicantService applicantService;
    private final ApplicantValidator applicantValidator;
    private final AnswerRepository answerRepository;
    private final RecruitmentService recruitmentService;

    @Transactional
    public void apply(final AnswerListRequest request) {
        validateDuplicatedAnswer(request);
        Recruitment recruitment = recruitmentService.getOnGoingRecruitment(LocalDateTime.now());
        final List<Object> spreadSheetContent = extractAnswersForSpreadSheets(request);
        spreadSheetsService.write(
                recruitment.getSpreadSheetId(),
                List.of(spreadSheetContent),
                recruitment.getGeneration());
        Applicant applicant =
                applicantService.saveApplicant(
                        request.memberProfile(), recruitment.getGeneration());
        answerRepository.save(new Answer(request.questionAndAnswerJson(), applicant));
    }

    public AnswerResponse findQuestionAndAnswer(final Long applicantId) {
        final Applicant applicant = applicantService.getApplicantById(applicantId);
        final Answer answer =
                answerRepository
                        .findByApplicant(applicant)
                        .orElseThrow(() -> new AnswerNotFoundException(ErrorCode.ANSWER_NOT_FOUND));
        return new AnswerResponse(answer.getQuestionAndAnswer());
    }

    private void validateDuplicatedAnswer(final AnswerListRequest request) {
        boolean alreadyApplied =
                applicantValidator.isAlreadyApplied(request.memberProfile().email());
        if (alreadyApplied) {
            throw new DuplicatedAnswerException(ErrorCode.DUPLICATED_ANSWER);
        }
    }

    private List<Object> extractAnswersForSpreadSheets(final AnswerListRequest request) {
        return request.answers().stream().map(a -> (Object) joinContents(a.contents())).toList();
    }

    private String joinContents(List<String> answers) {
        if (answers.size() == 1) {
            return answers.get(0);
        } else {
            return String.join(",", answers);
        }
    }
}
