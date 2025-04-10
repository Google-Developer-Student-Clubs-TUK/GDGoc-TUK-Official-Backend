package gdgoc.tuk.official.generationmember.controller;

import gdgoc.tuk.official.generationmember.dto.GenerationMemberListResponse;
import gdgoc.tuk.official.generationmember.service.GenerationMemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/generation-members")
@Tag(name = "기수 별 회원 API")
public class GenerationMemberController {

    private final GenerationMemberService generationMemberService;

    @GetMapping("/introduction/{generation}")
    @PreAuthorize("permitAll()")
    @Operation(summary = "People 화면 기수별 회원 조회", description = "기수 조회 API에서 응답한 최근 기수를 경로변수로 "
        + "주시면됩니다.")
    public GenerationMemberListResponse findMembersWithGeneration(@PathVariable String generation) {
        return generationMemberService.findGenerationMemberByGeneration(generation);
    }

    @GetMapping("/management")

}
