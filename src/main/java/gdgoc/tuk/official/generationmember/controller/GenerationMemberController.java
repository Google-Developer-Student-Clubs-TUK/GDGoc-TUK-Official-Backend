package gdgoc.tuk.official.generationmember.controller;

import gdgoc.tuk.official.generationmember.domain.GenerationMember;
import gdgoc.tuk.official.generationmember.dto.LoginCheckResponse;
import gdgoc.tuk.official.generationmember.dto.MemberIntroductionListResponse;
import gdgoc.tuk.official.generationmember.dto.MemberManagementPageResponse;
import gdgoc.tuk.official.generationmember.dto.MemberSearchCond;
import gdgoc.tuk.official.generationmember.service.GenerationMemberService;

import gdgoc.tuk.official.global.auth.GenerationMemberId;
import gdgoc.tuk.official.global.auth.GenerationMemberPrincipal;
import gdgoc.tuk.official.global.security.GdgMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/generation-members")
@Tag(name = "기수 별 회원 API")
@Slf4j
public class GenerationMemberController {

    private final GenerationMemberService generationMemberService;

    @GetMapping("/introduction/{generation}")
    @PreAuthorize("permitAll()")
    @Operation(summary = "기수별 회원 소개용 조회", description = "기수 조회 API에서 응답한 최근 기수를 경로변수로 " + "주시면됩니다.")
    public MemberIntroductionListResponse findMembersWithGeneration(
            @PathVariable String generation) {
        return generationMemberService.findGenerationMemberByGeneration(generation);
    }

    @GetMapping("/management")
    @Operation(summary = "회원 관리용 조회", description = "리더의 회원관리용 조회 API입니다.")
    public MemberManagementPageResponse searchMembers(
            MemberSearchCond memberSearchCond, Pageable pageable) {
        return generationMemberService.searchMembers(memberSearchCond, pageable);
    }

    @GetMapping
    @Operation(summary = "회원 로그인 확인", description = "로그인 된 회원인 지를 판별한 후 권한을 응답합니다.")
    public LoginCheckResponse loginCheck(@GenerationMemberPrincipal GenerationMember generationMember) {
        log.info("Generation Member : {}", generationMember);
        
        return new LoginCheckResponse(generationMember.getRole().getContent());
    }
}
