package gdgoc.tuk.official.recruitment.service;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.recruitment.domain.Recruitment;
import gdgoc.tuk.official.recruitment.exception.RecruitmentNotExistException;
import gdgoc.tuk.official.recruitment.repository.RecruitmentRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class RecruitmentGenerationService {

    private final RecruitmentRepository recruitmentRepository;

    public Recruitment getOnGoingRecruitment() {
        return recruitmentRepository
            .findByBetweenOpenAtAndCloseAt(LocalDateTime.now())
            .orElseThrow(
                () ->
                    new RecruitmentNotExistException(
                        ErrorCode.ON_GOING_RECRUITMENT_NOT_FOUND));
    }
}
