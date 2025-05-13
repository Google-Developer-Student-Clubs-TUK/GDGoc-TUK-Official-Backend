package gdgoc.tuk.official.recruitment.domain;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.recruitment.exception.InvalidGenerationException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recruitment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String generation;
    private String spreadSheetsId;
    private LocalDateTime openAt;
    private LocalDateTime closeAt;

    public Recruitment(
            final String generation,
            final String spreadSheetsId,
            final LocalDateTime openAt,
            final LocalDateTime closeAt) {
        validateGeneration(generation);
        this.generation = generation;
        this.spreadSheetsId = spreadSheetsId;
        this.openAt = openAt;
        this.closeAt = closeAt;
    }

    public Recruitment(final String generation) {
        this.generation = generation;
        this.spreadSheetsId = "spreadSheetsId";
        this.openAt = LocalDateTime.now().minusDays(5);
        this.closeAt = LocalDateTime.now().minusDays(5);
    }

    private void validateGeneration(final String generation) {
        String currentYear = String.valueOf(LocalDateTime.now().getYear());
        String nextYear = String.valueOf(LocalDateTime.now().plusYears(1L).getYear());
        if (!generation.equals(currentYear) && !generation.equals(nextYear)) {
            throw new InvalidGenerationException(ErrorCode.INVALID_GENERATION);
        }
    }
}
