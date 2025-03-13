package gdgoc.tuk.official.member.controller;

import gdgoc.tuk.official.member.dto.MemberListResponse;
import gdgoc.tuk.official.member.dto.RegisterRequest;
import gdgoc.tuk.official.member.dto.RegisterResponse;
import gdgoc.tuk.official.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Tag(name = "회원 API")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    @Operation(summary = "회원 가입")
    public RegisterResponse register(@RequestBody RegisterRequest request){
        return memberService.register(request);
    }

//    @GetMapping("/{generation}")
//    @Operation(summary = "기수별 회원 조회",description = "기수 조회 API에서 응답한 최근 기수를 경로변수로 주시면됩니다.")
//    public MemberListResponse findMembersWithGeneration(@PathVariable Integer generation){
//
//    }
}
