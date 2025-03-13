package gdgoc.tuk.official.generationmember.service;

import gdgoc.tuk.official.account.domain.Account;
import gdgoc.tuk.official.account.service.AccountService;
import gdgoc.tuk.official.applicant.domain.Applicant;
import gdgoc.tuk.official.generationmember.domain.GenerationMember;
import gdgoc.tuk.official.generationmember.repository.GenerationMemberRepository;
import gdgoc.tuk.official.generationmember.service.mapper.GenerationMemberMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerationMemberService {

    private final GenerationMemberMapper generationMemberMapper;
    private final GenerationMemberRepository generationMemberRepository;
    private final AccountService accountService;

    public void createGenerationMember(final Applicant applicant) {
        Account account = accountService.getAccountByEmail(applicant.getEmail());
        final GenerationMember generationMember =
                generationMemberMapper.toGenerationMember(applicant, account);
        generationMemberRepository.save(generationMember);
    }
}
