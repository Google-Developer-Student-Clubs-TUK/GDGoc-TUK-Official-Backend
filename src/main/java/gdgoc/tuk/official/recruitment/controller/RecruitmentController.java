package gdgoc.tuk.official.recruitment.controller;

import gdgoc.tuk.official.recruitment.dto.GenerationResponse;
import gdgoc.tuk.official.recruitment.dto.OpenRecruitmentResponse;
import gdgoc.tuk.official.recruitment.dto.RecruitmentOpenRequest;
import gdgoc.tuk.official.recruitment.dto.RecruitmentStatusResponse;
import gdgoc.tuk.official.recruitment.service.RecruitmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recruitments")
@Tag(name = "모집 API")
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    @PostMapping
    //    @PreAuthorize("hasRole('LEADER')")
    @Operation(summary = "모집 시작", description = "리더 전용 API로 모집을 시작하는 API입니다.")
    public OpenRecruitmentResponse openRecruitment(@Valid @RequestBody final RecruitmentOpenRequest request) {
        return recruitmentService.openRecruitment(request);
    }

    @GetMapping("/generations")
    @PreAuthorize("permitAll()")
    @Operation(summary = "전체 모집 기수(Generation) 정보", description = "기수 정보 리스트를 반환합니다.")
    public GenerationResponse getRecentGeneration() {
        return recruitmentService.getGenerations();
    }

    @GetMapping("/status")
    @PreAuthorize("permitAll()")
    @Operation(summary = "모집 시작 여부", description = "현재 날짜를 기준으로 모집이 진행 중인지 아닌지를 반환합니다.")
    public RecruitmentStatusResponse getRecruitmentStatus() {
        return recruitmentService.getRecruitmentStatus();
    }
}
