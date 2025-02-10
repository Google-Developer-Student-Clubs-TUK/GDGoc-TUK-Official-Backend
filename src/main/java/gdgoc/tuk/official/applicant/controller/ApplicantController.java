package gdgoc.tuk.official.applicant.controller;

import gdgoc.tuk.official.applicant.service.ApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applicants")
public class ApplicantController {

    private final ApplicantService applicantService;

    @PostMapping("/approve")
    public void approve(){

    }

    @PatchMapping("/reject")
    public void reject(){

    }
}
