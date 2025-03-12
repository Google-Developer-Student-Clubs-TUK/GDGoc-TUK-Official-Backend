package gdgoc.tuk.official.member.service.mapper;

import gdgoc.tuk.official.applicant.domain.Applicant;
import gdgoc.tuk.official.member.domain.Account;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    
    public Account fromApplicant(final Applicant applicant,final String encodedPassword){
        return Account.builder()
            .email(applicant.getEmail())
            .field(applicant.getField())
            .role(applicant.getRole())
            .gender(applicant.getGender())
            .password(encodedPassword)
            .build();
    }
}
