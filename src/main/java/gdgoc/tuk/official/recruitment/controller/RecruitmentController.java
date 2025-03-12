package gdgoc.tuk.official.recruitment.controller;

import gdgoc.tuk.official.recruitment.dto.GenerationResponse;
import gdgoc.tuk.official.recruitment.dto.RecruitmentOpenRequest;
import gdgoc.tuk.official.recruitment.service.RecruitmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recruitments")
public class RecruitmentController {

  private final RecruitmentService recruitmentService;

  @PostMapping
  public void openRecruitment(@RequestBody final RecruitmentOpenRequest request) {
    recruitmentService.openRecruitment(request);
  }

  @GetMapping("/generations")
  public GenerationResponse getRecentGeneration(){
    return recruitmentService.getGenerations();
  }
}
