package gdgoc.tuk.official.recruitment.service;

import gdgoc.tuk.official.answer.repository.SpreadSheetsPrimaryKeyRepository;
import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.google.initializer.SpreadSheetsInitializer;
import gdgoc.tuk.official.google.service.SpreadSheetsQuestionService;
import gdgoc.tuk.official.recruitment.domain.Recruitment;
import gdgoc.tuk.official.recruitment.dto.GenerationResponse;
import gdgoc.tuk.official.recruitment.dto.RecruitmentOpenRequest;
import gdgoc.tuk.official.recruitment.exception.GenerationDuplicationException;
import gdgoc.tuk.official.recruitment.exception.RecruitmentDuplicationException;
import gdgoc.tuk.official.recruitment.repository.RecruitmentRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final SpreadSheetsInitializer spreadSheetsInitializer;
    private final SpreadSheetsPrimaryKeyRepository spreadSheetsPrimaryKeyRepository;
    private final SpreadSheetsQuestionService spreadSheetsQuestionService;

    @Transactional
    public void openRecruitment(final RecruitmentOpenRequest request) {
        checkGeneration(request.generation());
        checkAlreadyExistRecruitment();
        spreadSheetsPrimaryKeyRepository.saveNewGeneration(request.generation());
        final String spreadSheetsId =
                spreadSheetsInitializer.init(request.generation(), spreadSheetsQuestionService.getSpreadSheetQuestions());
        final Recruitment recruitment =
                new Recruitment(
                        request.generation(), spreadSheetsId, request.openAt(), request.closeAt());
        recruitmentRepository.save(recruitment);
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

    public GenerationResponse getGenerations() {
        List<String> generations =
                recruitmentRepository.findGenerationByLocalTime(LocalDateTime.now()).stream()
                        .map(Recruitment::getGeneration)
                        .toList();
        return new GenerationResponse(generations);
    }
}
