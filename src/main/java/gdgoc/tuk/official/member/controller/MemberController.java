package gdgoc.tuk.official.member.controller;

import gdgoc.tuk.official.member.dto.MemberListResponse;
import gdgoc.tuk.official.member.dto.RegisterRequest;
import gdgoc.tuk.official.member.dto.RegisterResponse;
import gdgoc.tuk.official.member.service.MemberService;
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
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest request){
        return memberService.register(request);
    }

    @GetMapping("/{generation}")
    public MemberListResponse findMembersWithGeneration(@PathVariable Integer generation){

    }
}
