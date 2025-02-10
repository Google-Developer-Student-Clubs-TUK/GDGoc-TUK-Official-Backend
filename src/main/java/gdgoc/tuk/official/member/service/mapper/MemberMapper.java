package gdgoc.tuk.official.member.service.mapper;

import gdgoc.tuk.official.applicant.domain.Applicant;
import gdgoc.tuk.official.member.domain.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    
    public Member fromApplicant(final Applicant applicant,final String encodedPassword){
        return Member.builder()
            .email(applicant.getEmail())
            .field(applicant.getField())
            .role(applicant.getRole())
            .gender(applicant.getGender())
            .password(encodedPassword)
            .build();
    }
}
