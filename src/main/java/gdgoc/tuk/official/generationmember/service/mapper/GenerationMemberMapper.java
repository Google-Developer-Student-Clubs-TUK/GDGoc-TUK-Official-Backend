package gdgoc.tuk.official.generationmember.service.mapper;

import gdgoc.tuk.official.applicant.domain.Applicant;
import gdgoc.tuk.official.account.domain.Account;
import gdgoc.tuk.official.generationmember.domain.GenerationMember;
import org.springframework.stereotype.Component;

@Component
public class GenerationMemberMapper {

    public GenerationMember toGenerationMember(final Applicant applicant, final Account account) {
        return GenerationMember.builder()
                .enrollmentStatus(applicant.getEnrollmentStatus())
                .generation(applicant.getGeneration())
                .universityYear(applicant.getUniversityYear())
                .field(applicant.getField())
                .gender(applicant.getGender())
                .account(account)
                .build();
    }
}
