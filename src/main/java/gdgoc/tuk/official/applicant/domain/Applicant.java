package gdgoc.tuk.official.applicant.domain;

import gdgoc.tuk.official.member.domain.Field;
import gdgoc.tuk.official.member.domain.Gender;
import gdgoc.tuk.official.member.domain.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Applicant {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String studentNumber;

  @Enumerated(EnumType.STRING)
  private Field field;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  private String email;

  private String major;

  @Enumerated(EnumType.STRING)
  private ApplicationStatus applicationStatus;

  @Enumerated(EnumType.STRING)
  private Role role;

  public Applicant(
      final String name,
      final String studentNumber,
      final Field field,
      final Gender gender,
      final String email,
      String major) {
    this.name = name;
    this.studentNumber = studentNumber;
    this.field = field;
    this.gender = gender;
    this.email = email;
    this.major = major;
    this.applicationStatus = ApplicationStatus.PENDING;
    this.role = Role.ROLE_ORGANIZER;
  }
}
