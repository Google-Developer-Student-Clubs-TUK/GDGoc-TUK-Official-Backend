package gdgoc.tuk.official.applicant.controller;

import gdgoc.tuk.official.applicant.dto.ApplicantPageResponse;
import gdgoc.tuk.official.applicant.dto.ApplicantRoleRequest;
import gdgoc.tuk.official.applicant.service.ApplicantService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import javax.mail.MessagingException;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applicants")
@Tag(name = "지원자 관리 API")
public class ApplicantController {

    private final ApplicantService applicantService;

    @PostMapping("/{applicantId}")
//    @PreAuthorize("hasRole('LEADER')")
    @Operation(summary = "합격", description = "리더 전용 API로 지원자를 합격시킵니다.")
    public void approve(@PathVariable final Long applicantId,
        @RequestBody ApplicantRoleRequest request) throws MessagingException, IOException {
        applicantService.approve(applicantId,request);
    }

    @PatchMapping("/{applicantId}")
//    @PreAuthorize("hasRole('LEADER')")
    @Operation(summary = "불합격", description = "리더 전용 API로 지원자를 불합격시킵니다.")
    public void reject(@PathVariable final Long applicantId) {
        applicantService.reject(applicantId);
    }

    @GetMapping
//    @PreAuthorize("hasRole('LEADER')")
    @Operation(summary = "지원자 목록 조회", description = "모든 지원자를 조회합니다.")
    public ApplicantPageResponse findApplicantsList(final Pageable pageable) {
        return applicantService.findAllApplicants(pageable);
    }
}
