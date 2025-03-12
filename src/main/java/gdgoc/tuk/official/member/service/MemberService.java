package gdgoc.tuk.official.member.service;

import gdgoc.tuk.official.member.domain.Account;
import gdgoc.tuk.official.member.dto.RegisterRequest;
import gdgoc.tuk.official.member.dto.RegisterResponse;
import gdgoc.tuk.official.member.repository.AccountRepository;
import gdgoc.tuk.official.member.service.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final AccountRepository accountRepository;
  private final AccountService accountService;
  private final MemberMapper memberMapper;

  public RegisterResponse register(final RegisterRequest request) {
    final Account account = accountService.createFromRequest(request);
    accountRepository.save(account);
    return new RegisterResponse(account.getId(),request.email());
  }

  public void findMembersWithGeneration(){
    
  }
}
