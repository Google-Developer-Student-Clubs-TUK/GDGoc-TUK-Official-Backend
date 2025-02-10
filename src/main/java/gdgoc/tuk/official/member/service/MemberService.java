package gdgoc.tuk.official.member.service;

import gdgoc.tuk.official.member.domain.Member;
import gdgoc.tuk.official.member.dto.RegisterRequest;
import gdgoc.tuk.official.member.dto.RegisterResponse;
import gdgoc.tuk.official.member.repository.MemberRepository;
import gdgoc.tuk.official.member.service.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final MemberRegisterService memberRegisterService;
  private final MemberMapper memberMapper;

  public RegisterResponse register(final RegisterRequest request) {
    final Member member = memberRegisterService.createFromRequest(request);
    memberRepository.save(member);
    return new RegisterResponse(member.getId(),request.email());
  }
}
