package gdgoc.tuk.official.applicant.service.mapper;

import gdgoc.tuk.official.answer.dto.MemberProfile;
import gdgoc.tuk.official.applicant.domain.Applicant;
import gdgoc.tuk.official.applicant.dto.ApplicantPageResponse;
import gdgoc.tuk.official.applicant.dto.ApplicantPageResponse.ApplicantInfo;

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

    public List<ApplicantInfo> toApplicantResponse(List<Applicant> applicants) {
        return applicants.stream()
                .map(
                        a ->
                                ApplicantInfo.builder()
                                        .applicantId(a.getId())
                                        .major(a.getMajor())
                                        .enrollmentStatus(a.getEnrollmentStatus())
                                        .field(a.getField())
                                        .gender(a.getGender())
                                        .name(a.getName())
                                        .build())
                .toList();
    }
}
