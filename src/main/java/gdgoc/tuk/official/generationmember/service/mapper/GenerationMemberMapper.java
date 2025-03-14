package gdgoc.tuk.official.generationmember.service.mapper;

import gdgoc.tuk.official.account.domain.Accounts;
import gdgoc.tuk.official.applicant.domain.Applicant;
import gdgoc.tuk.official.generationmember.domain.GenerationMember;
import gdgoc.tuk.official.generationmember.dto.GenerationMemberListResponse;
import gdgoc.tuk.official.generationmember.dto.GenerationMemberResponse;
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

    public GenerationMemberListResponse toGenerationMemberResponse(final List<GenerationMember> generationMembers){
        List<GenerationMemberResponse> generationMemberResponses = generationMembers.stream()
            .map(gm -> new GenerationMemberResponse(gm.getName(),
                gm.getField(), gm.getGender())).toList();
        return new GenerationMemberListResponse(generationMemberResponses);
    }
}