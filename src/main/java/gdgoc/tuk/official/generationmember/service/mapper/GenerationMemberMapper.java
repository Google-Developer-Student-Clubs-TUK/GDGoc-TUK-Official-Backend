package gdgoc.tuk.official.generationmember.service.mapper;

import gdgoc.tuk.official.account.domain.Accounts;
import gdgoc.tuk.official.applicant.domain.Applicant;
import gdgoc.tuk.official.generationmember.domain.GenerationMember;
import gdgoc.tuk.official.generationmember.dto.MemberIntroductionListResponse;
import gdgoc.tuk.official.generationmember.dto.MemberIntroductionResponse;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GenerationMemberMapper {

    public GenerationMember toGenerationMember(final Applicant applicant, final Accounts accounts) {
        return GenerationMember.builder()
                .enrollmentStatus(applicant.getEnrollmentStatus())
                .generation(applicant.getGeneration())
                .universityYear(applicant.getUniversityYear())
                .field(applicant.getField())
                .gender(applicant.getGender())
                .accounts(accounts)
                .major(applicant.getMajor())
                .name(applicant.getName())
                .studentNumber(applicant.getStudentNumber())
                .build();
    }

    public MemberIntroductionListResponse toGenerationMemberResponse(final List<GenerationMember> generationMembers){
        List<MemberIntroductionResponse> memberIntroductionRespons = generationMembers.stream()
            .map(gm -> new MemberIntroductionResponse(gm.getName(),
                gm.getField(), gm.getGender())).toList();
        return new MemberIntroductionListResponse(memberIntroductionRespons);
    }
}