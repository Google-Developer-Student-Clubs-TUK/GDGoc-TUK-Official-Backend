package gdgoc.tuk.official.recruitment.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recruitment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Integer generation;
  private LocalDateTime openAt;
  private LocalDateTime closeAt;

  public Recruitment(
      final Integer generation, final LocalDateTime openAt, final LocalDateTime closeAt) {
    this.generation = generation;
    this.openAt = openAt;
    this.closeAt = closeAt;
  }
}
