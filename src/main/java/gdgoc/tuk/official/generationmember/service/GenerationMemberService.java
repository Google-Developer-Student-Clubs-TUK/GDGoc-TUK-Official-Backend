package gdgoc.tuk.official.generationmember.service;

import gdgoc.tuk.official.account.domain.Accounts;
import gdgoc.tuk.official.account.service.AccountService;
import gdgoc.tuk.official.applicant.domain.Applicant;
import gdgoc.tuk.official.generationmember.domain.GenerationMember;
import gdgoc.tuk.official.generationmember.dto.GenerationMemberListResponse;
import gdgoc.tuk.official.generationmember.exception.DuplicatedGenerationMemberException;
import gdgoc.tuk.official.generationmember.repository.GenerationMemberRepository;
import gdgoc.tuk.official.generationmember.service.mapper.GenerationMemberMapper;

import gdgoc.tuk.official.global.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerationMemberService {

    private final GenerationMemberMapper generationMemberMapper;
    private final GenerationMemberRepository generationMemberRepository;

    public void createGenerationMember(final Applicant applicant,final Accounts accounts) {
        checkDuplicateGenerationMember(accounts,applicant.getGeneration());
        final GenerationMember generationMember =
                generationMemberMapper.toGenerationMember(applicant, accounts);
        generationMemberRepository.save(generationMember);
    }

    private void checkDuplicateGenerationMember(final Accounts accounts, final String generation) {
        if(generationMemberRepository.existsByAccountsAndGeneration(accounts,generation)){
            throw new DuplicatedGenerationMemberException(ErrorCode.DUPLICATED_GENERATION_MEMBER);
        }
    }

    public GenerationMemberListResponse findGenerationMemberByGeneration(final String generation){
        List<GenerationMember> generationMembers = generationMemberRepository.findByGeneration(
            generation);
        return generationMemberMapper.toGenerationMemberResponse(generationMembers);
    }
}
