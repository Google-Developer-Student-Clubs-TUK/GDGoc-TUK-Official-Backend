package gdgoc.tuk.official.applicant.dto;

import gdgoc.tuk.official.generationmember.domain.EnrollmentStatus;
import gdgoc.tuk.official.generationmember.domain.Field;
import gdgoc.tuk.official.generationmember.domain.Gender;
import gdgoc.tuk.official.generationmember.domain.UniversityYear;

import lombok.Builder;

import java.util.List;

public record ApplicantResponse(List<ApplicantInfo> applicants) {
    @Builder
    public record ApplicantInfo(
            Long applicantId,
            String name,
            String studentNumber,
            EnrollmentStatus enrollmentStatus,
            UniversityYear universityYear,
            Field field,
            Gender gender,
            String email,
            String major) {}
}
