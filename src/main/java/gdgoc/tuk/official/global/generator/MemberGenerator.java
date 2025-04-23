package gdgoc.tuk.official.global.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import gdgoc.tuk.official.account.domain.Accounts;
import gdgoc.tuk.official.account.domain.Role;
import gdgoc.tuk.official.account.repository.AccountRepository;
import gdgoc.tuk.official.answer.domain.Answer;
import gdgoc.tuk.official.answer.repository.AnswerRepository;
import gdgoc.tuk.official.applicant.domain.Applicant;
import gdgoc.tuk.official.applicant.repository.ApplicantRepository;
import gdgoc.tuk.official.generationmember.domain.EnrollmentStatus;
import gdgoc.tuk.official.generationmember.domain.Field;
import gdgoc.tuk.official.generationmember.domain.Gender;
import gdgoc.tuk.official.generationmember.domain.GenerationMember;
import gdgoc.tuk.official.generationmember.domain.UniversityYear;
import gdgoc.tuk.official.generationmember.repository.GenerationMemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberGenerator implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final GenerationMemberRepository generationMemberRepository;
    private final ApplicantRepository applicantRepository;
    private final AnswerRepository answerRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void run(final String... args) throws Exception {
        String[] majors = {"컴퓨터공학과", "미디어디자인공학과", "소프트웨어학과", "IT경영학과"};
        for (int i = 0; i < 10; i++) {
            Faker faker = new Faker();
            Accounts save =
                    accountRepository.save(
                            new Accounts(
                                    faker.name().lastName() + "@tukorea.ac.kr",
                                    "1234",
                                    Role.ROLE_MEMBER));
            generationMemberRepository.save(
                    new GenerationMember(
                            save,
                            faker.name().lastName(),
                            faker.expression("#{numerify '20########'}"),
                            Field.values()[i % 3],
                            Gender.values()[i % 2],
                            EnrollmentStatus.values()[i % 2],
                            UniversityYear.values()[i % 4],
                            majors[i % 4],
                            "2024",
                            save.getRole()));
        }

        for (int i = 0; i < 10; i++) {
            Faker faker = new Faker();
            Applicant save = applicantRepository.save(
                new Applicant(
                    faker.name().lastName(),
                    faker.expression("#{numerify '20########'}"),
                    EnrollmentStatus.values()[i % 2],
                    UniversityYear.values()[i % 4],
                    Field.values()[i % 3],
                    Gender.values()[i % 2],
                    faker.name().lastName() + "tukorea.ac.kr",
                    majors[i % 4],
                    "2025",
                    Role.values()[i % 3]));
            String s = objectMapper.writeValueAsString(new ApplicantAnswer());
            answerRepository.save(new Answer(s, save));
        }
    }
    @Getter
    public static class ApplicantAnswer{
        String 이름;
        String 학과;
        String 지원동기;
        String 직군;

        public ApplicantAnswer() {
            this.이름 = "몰라여";
            this.학과 = "몰라여";
            this.지원동기 = "몰라여";
            this.직군 = "몰라여";
        }
    }
}
