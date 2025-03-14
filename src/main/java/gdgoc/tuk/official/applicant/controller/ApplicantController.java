package gdgoc.tuk.official.applicant.controller;

import gdgoc.tuk.official.applicant.dto.ApplicantResponse;
import gdgoc.tuk.official.applicant.service.ApplicantService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applicants")
public class ApplicantController {

    private final ApplicantService applicantService;

    @PostMapping("/{applicantId}")
    @PreAuthorize("hasRole('LEADER')")
    public void approve(@PathVariable final Long applicantId) {
        applicantService.approve(applicantId);
    }

    @PatchMapping("/{applicantId}")
    @PreAuthorize("hasRole('LEADER')")
    public void reject(@PathVariable final Long applicantId) {
        applicantService.reject(applicantId);
    }

    @GetMapping
    @PreAuthorize("hasRole('LEADER')")
    public ApplicantResponse findApplicants() {
        return applicantService.findAllApplicants();
    }
}
