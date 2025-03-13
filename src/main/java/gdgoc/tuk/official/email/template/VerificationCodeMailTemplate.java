package gdgoc.tuk.official.email.template;

public class VerificationCodeMailTemplate {
    private static final String content =
            """
    <div style="font-family:Arial,sans-serif;padding:20px;background:#f4f4f4;">
        <div style="max-width:600px;margin:auto;background:#fff;padding:20px;border-radius:8px;box-shadow:0 0 10px rgba(0,0,0,0.1);">
            <h2 style="color:#333;">GDGoC TUK 메일 인증 코드</h2>
            <p id="code" style="font-size:20px;font-weight:bold;color:#007bff;">%s</p>
            <p style="color:#666;">인증 코드는 10분간 유효합니다.</p>
        </div>
    </div>
""";

    public static String code(final String code) {
        return content.formatted(code);
    }
}
