package gdgoc.tuk.official.email.template;

public class WelcomMailTemplate {

    public static final String TITLE = "GDGoC TUK %s기 지원 결과 안내";

    private static final String CONTENT =
        """
        <div class="container">
        <h2 class="header">🎉 GDGoC TUK %s기 합격을 축하합니다! 🎉</h2>
        <p class="message">
    안녕하세요, <strong>Google Developer Groups on Campus TUK</strong>입니다.<br>
    귀하께서 GDGoC TUK의 일원이 되신 것을 진심으로 환영합니다!
        </p>
        <p class="message">
    앞으로 함께할 멋진 활동들이 기대되며, 다양한 프로젝트와 네트워킹 기회를 경험하시길 바랍니다.
        </p>
        <p class="message">
    가입을 완료하려면 아래의 로그인 정보를 확인해주세요:
        </p>
        <p class="highlight">아이디: 지원서에 기입한 한국공학대학교 이메일 <br>비밀번호: 지원서에 기입한 학번</p>
        <p class="message">보안을 위해 로그인 후 비밀번호를 변경하시길 권장드립니다.</p>
        <p class="footer">GDGoC TUK 운영진 드림</p>
    </div>
""";

    public static String body(final String generation) {
        return CONTENT.formatted(generation);
    }

    public static String title(final String generation) {
        return TITLE.formatted(generation);
    }
}
