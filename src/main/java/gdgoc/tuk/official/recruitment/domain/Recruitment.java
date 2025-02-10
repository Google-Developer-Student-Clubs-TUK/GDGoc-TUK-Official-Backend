package gdgoc.tuk.official.recruitment.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    this.generation = generation;
    this.spreadSheetsId = spreadSheetsId;
    this.openAt = openAt;
    this.closeAt = closeAt;
  }
}
