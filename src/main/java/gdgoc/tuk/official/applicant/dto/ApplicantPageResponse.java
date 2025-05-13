package gdgoc.tuk.official.applicant.dto;

import gdgoc.tuk.official.account.domain.Role;
import gdgoc.tuk.official.generationmember.domain.EnrollmentStatus;
import gdgoc.tuk.official.generationmember.domain.Field;
import gdgoc.tuk.official.generationmember.domain.Gender;

import lombok.Builder;

import java.util.List;

public record ApplicantPageResponse(
        int totalPage, int currentPage, List<ApplicantInfo> applicants) {
    @Builder
    public record ApplicantInfo(
            Long applicantId,
            String name,
            EnrollmentStatus enrollmentStatus,
            Field field,
            Gender gender,
            String major,
            Role role) {}
}
