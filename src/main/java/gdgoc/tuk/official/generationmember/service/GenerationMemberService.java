package gdgoc.tuk.official.generationmember.service;

import gdgoc.tuk.official.account.domain.Accounts;
import gdgoc.tuk.official.applicant.domain.Applicant;
import gdgoc.tuk.official.generationmember.domain.GenerationMember;
import gdgoc.tuk.official.generationmember.dto.MemberIntroductionListResponse;
import gdgoc.tuk.official.generationmember.dto.MemberManagementPageResponse;
import gdgoc.tuk.official.generationmember.dto.MemberManagementResponse;
import gdgoc.tuk.official.generationmember.dto.MemberSearchCond;
import gdgoc.tuk.official.generationmember.exception.DuplicatedGenerationMemberException;
import gdgoc.tuk.official.generationmember.repository.GenerationMemberRepositoryRepository;
import gdgoc.tuk.official.generationmember.service.mapper.GenerationMemberMapper;
import gdgoc.tuk.official.global.ErrorCode;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenerationMemberService {

    private final GenerationMemberMapper generationMemberMapper;
    private final GenerationMemberRepositoryRepository generationMemberRepository;

    public void createGenerationMember(final Applicant applicant, final Accounts accounts) {
        checkDuplicateGenerationMember(accounts, applicant.getGeneration());
        final GenerationMember generationMember =
                generationMemberMapper.toGenerationMember(applicant, accounts);
        generationMemberRepository.save(generationMember);
    }

    private void checkDuplicateGenerationMember(final Accounts accounts, final String generation) {
        if (generationMemberRepository.existsByAccountsAndGeneration(accounts, generation)) {
            throw new DuplicatedGenerationMemberException(ErrorCode.DUPLICATED_GENERATION_MEMBER);
        }
    }

    public MemberIntroductionListResponse findGenerationMemberByGeneration(
            final String generation) {
        List<GenerationMember> generationMembers =
                generationMemberRepository.findByGeneration(generation);
        return generationMemberMapper.toGenerationMemberResponse(generationMembers);
    }

    public MemberManagementPageResponse searchMembers(
            MemberSearchCond memberSearchCond, Pageable pageable) {
        Page<GenerationMember> page =
                generationMemberRepository.findByCondition(memberSearchCond, pageable);

        return new MemberManagementPageResponse(
                page.getTotalPages(),
                page.getNumber(),
                generationMemberMapper.toMemberManagementResponse(page.getContent()));
    }
}
