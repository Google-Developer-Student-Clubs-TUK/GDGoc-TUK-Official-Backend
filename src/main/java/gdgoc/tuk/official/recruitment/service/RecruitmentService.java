package gdgoc.tuk.official.recruitment.service;

import gdgoc.tuk.official.answer.repository.SpreadSheetsPrimaryKeyRepository;
import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.google.initializer.SpreadSheetsInitializer;
import gdgoc.tuk.official.google.service.SpreadSheetsQuestionService;
import gdgoc.tuk.official.recruitment.domain.Recruitment;
import gdgoc.tuk.official.recruitment.dto.GenerationResponse;
import gdgoc.tuk.official.recruitment.dto.OpenRecruitmentResponse;
import gdgoc.tuk.official.recruitment.dto.RecruitmentOpenRequest;
import gdgoc.tuk.official.recruitment.dto.RecruitmentStatusResponse;
import gdgoc.tuk.official.recruitment.dto.SpreadSheetInformation;
import gdgoc.tuk.official.recruitment.exception.RecruitmentDuplicationException;
import gdgoc.tuk.official.recruitment.exception.RecruitmentNotExistException;
import gdgoc.tuk.official.recruitment.repository.RecruitmentRepository;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final SpreadSheetsInitializer spreadSheetsInitializer;
    private final SpreadSheetsPrimaryKeyRepository spreadSheetsPrimaryKeyRepository;
    private final SpreadSheetsQuestionService spreadSheetsQuestionService;

    @Transactional
    public OpenRecruitmentResponse openRecruitment(final RecruitmentOpenRequest request) {
        validateNoOngoingRecruitment();
        spreadSheetsPrimaryKeyRepository.saveNewGeneration(request.generation());
        final SpreadSheetInformation spreadSheetInformation =
                spreadSheetsInitializer.init(
                        request.generation(),
                        spreadSheetsQuestionService.getSpreadSheetQuestions());
        log.info("openRecruitment sheets id : {}", spreadSheetInformation.spreadSheetId());
        final Recruitment recruitment =
                new Recruitment(
                        request.generation(),
                        spreadSheetInformation.spreadSheetId(),
                        spreadSheetInformation.spreadSheetUrl(),
                        request.openAt(),
                        request.closeAt());
        recruitmentRepository.save(recruitment);
        return new OpenRecruitmentResponse(spreadSheetInformation.spreadSheetUrl(),
            spreadSheetInformation.spreadSheetId()
            ,request.generation());
    }

    public RecruitmentStatusResponse getCurrentRecruitmentStatus() {
        LocalDateTime now = LocalDateTime.now();
        return recruitmentRepository
                .findByBetweenOpenAtAndCloseAt(now)
                .map(r -> new RecruitmentStatusResponse(true))
                .orElseGet(() -> new RecruitmentStatusResponse(false));
    }

    public RecruitmentStatusResponse getFutureRecruitmentStatus() {
        LocalDateTime now = LocalDateTime.now();
        return recruitmentRepository
            .findByOpenAtAfterOrderByOpenAtAsc(now)
            .map(r -> new RecruitmentStatusResponse(true))
            .orElseGet(() -> new RecruitmentStatusResponse(false));
    }

    public Recruitment getOnGoingRecruitment(final LocalDateTime now) {
        return recruitmentRepository
                .findByBetweenOpenAtAndCloseAt(now)
                .orElseThrow(
                        () ->
                                new RecruitmentNotExistException(
                                        ErrorCode.ON_GOING_RECRUITMENT_NOT_FOUND));
    }

    private void validateNoOngoingRecruitment() {
        LocalDateTime currentTime = LocalDateTime.now();
        if (recruitmentRepository.existsByCloseAtIsAfter(currentTime)) {
            throw new RecruitmentDuplicationException(ErrorCode.RECRUITMENT_ALREADY_EXIST);
        }
    }

    public GenerationResponse getGenerations() {
        Set<String> generations =
                recruitmentRepository.findGenerationByLocalTime(LocalDateTime.now()).stream()
                        .map(Recruitment::getGeneration)
                        .collect(Collectors.toSet());
        return new GenerationResponse(generations);
    }
}
