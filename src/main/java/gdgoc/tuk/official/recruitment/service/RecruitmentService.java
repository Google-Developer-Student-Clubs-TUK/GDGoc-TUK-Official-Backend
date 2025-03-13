package gdgoc.tuk.official.recruitment.service;

import gdgoc.tuk.official.answer.repository.NextAnswerRowRedisRepository;
import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.google.initializer.SpreadSheetsInitializer;
import gdgoc.tuk.official.question.domain.Question;
import gdgoc.tuk.official.question.service.QuestionService;
import gdgoc.tuk.official.questionorder.domain.QuestionOrders;
import gdgoc.tuk.official.questionorder.service.QuestionOrderService;
import gdgoc.tuk.official.recruitment.domain.Recruitment;
import gdgoc.tuk.official.recruitment.dto.GenerationResponse;
import gdgoc.tuk.official.recruitment.dto.RecruitmentOpenRequest;
import gdgoc.tuk.official.recruitment.exception.GenerationDuplicationException;
import gdgoc.tuk.official.recruitment.exception.RecruitmentDuplicationException;
import gdgoc.tuk.official.recruitment.exception.RecruitmentNotExistException;
import gdgoc.tuk.official.recruitment.repository.RecruitmentRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final QuestionService questionService;
    private final QuestionOrderService questionOrderService;
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
                new Recruitment(
                        request.generation(), spreadSheetsId, request.openAt(), request.closeAt());
        recruitmentRepository.save(recruitment);
    }

    private List<List<Object>> getSpreadSheetQuestions() {
        Map<Long, Integer> orderMap =
                questionOrderService.findAllQuestionOrders().stream()
                        .collect(
                                Collectors.toMap(
                                        QuestionOrders::getQuestionId, QuestionOrders::getOrders));
        List<Question> sortedQuestions =
                questionService.findAllQuestions().stream()
                        .sorted(
                                Comparator.comparing(
                                        q -> orderMap.getOrDefault(q.getId(), Integer.MAX_VALUE)))
                        .toList();
        return List.of(sortedQuestions.stream().map(q -> (Object) q.getContent()).toList());
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

    public Recruitment getOnGoingRecruitment() {
        return recruitmentRepository
                .findByBetweenOpenAtAndCloseAt(LocalDateTime.now())
                .orElseThrow(
                        () ->
                                new RecruitmentNotExistException(
                                        ErrorCode.ON_GOING_RECRUITMENT_NOT_FOUND));
    }

    public GenerationResponse getGenerations() {
        List<String> generations =
                recruitmentRepository.findGenerationByLocalTime(LocalDateTime.now()).stream()
                        .map(Recruitment::getGeneration)
                        .toList();
        return new GenerationResponse(generations);
    }
}
