package gdgoc.tuk.official.email.dto;

public record EmailVerificationRequest(
    String code,
    String email
) {
}