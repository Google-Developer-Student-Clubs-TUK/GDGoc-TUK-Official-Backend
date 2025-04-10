package gdgoc.tuk.official.applicant.service.mapper;

import gdgoc.tuk.official.answer.dto.MemberProfile;
import gdgoc.tuk.official.applicant.domain.Applicant;
import gdgoc.tuk.official.applicant.dto.ApplicantResponse;
import gdgoc.tuk.official.applicant.dto.ApplicantResponse.ApplicantInfo;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicantMapper {

    public Applicant toApplicant(final MemberProfile memberProfile, final String generation) {
        return Applicant.builder()
                .major(memberProfile.major())
                .email(memberProfile.email())
                .enrollmentStatus(memberProfile.enrollmentStatus())
                .field(memberProfile.field())
                .gender(memberProfile.gender())
                .name(memberProfile.name())
                .studentNumber(memberProfile.studentNumber())
                .universityYear(memberProfile.universityYear())
                .generation(generation)
                .role(memberProfile.role())
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
