package gdgoc.tuk.official.applicant.service.mapper;

import gdgoc.tuk.official.answer.dto.RequiredAnswer;
import gdgoc.tuk.official.applicant.domain.Applicant;
import gdgoc.tuk.official.applicant.dto.ApplicantResponse;
import gdgoc.tuk.official.applicant.dto.ApplicantResponse.ApplicantInfo;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicantMapper {

    public Applicant toApplicant(final RequiredAnswer requiredAnswer, final String generation) {
        return Applicant.builder()
                .major(requiredAnswer.major())
                .email(requiredAnswer.email())
                .enrollmentStatus(requiredAnswer.enrollmentStatus())
                .field(requiredAnswer.field())
                .gender(requiredAnswer.gender())
                .name(requiredAnswer.name())
                .studentNumber(requiredAnswer.studentNumber())
                .universityYear(requiredAnswer.universityYear())
                .generation(generation)
                .build();
    }

    public ApplicantResponse toApplicantResponse(List<Applicant> applicants) {
        List<ApplicantInfo> applicantInfos =
                applicants.stream()
                        .map(
                                a ->
                                        ApplicantInfo.builder()
                                                .applicantId(a.getId())
                                                .major(a.getMajor())
                                                .email(a.getEmail())
                                                .enrollmentStatus(a.getEnrollmentStatus())
                                                .field(a.getField())
                                                .gender(a.getGender())
                                                .name(a.getName())
                                                .studentNumber(a.getStudentNumber())
                                                .universityYear(a.getUniversityYear())
                                                .build())
                        .toList();
        return new ApplicantResponse(applicantInfos);
    }
}
