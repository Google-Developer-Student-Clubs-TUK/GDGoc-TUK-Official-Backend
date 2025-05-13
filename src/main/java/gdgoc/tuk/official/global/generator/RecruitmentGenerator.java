package gdgoc.tuk.official.global.generator;

import gdgoc.tuk.official.recruitment.domain.Recruitment;
import gdgoc.tuk.official.recruitment.repository.RecruitmentRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RecruitmentGenerator implements CommandLineRunner {

    private final RecruitmentRepository recruitmentRepository;

    @Override
    public void run(final String... args) throws Exception {
        recruitmentRepository.saveAll(List.of(new Recruitment("2024"), new Recruitment("2025")));
    }
}
