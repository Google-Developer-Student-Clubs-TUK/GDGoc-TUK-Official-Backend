package gdgoc.tuk.official.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import gdgoc.tuk.official.global.security.GdgMember;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper objectMapper;

    public LoginSuccessHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        GdgMember member = (GdgMember) authentication.getPrincipal();
        LoginSuccessResponse loginResponse = new LoginSuccessResponse(member.getRole(),member.getUsername());

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        objectMapper.writeValue(response.getWriter(), loginResponse);
    }

    private record LoginSuccessResponse(String role, String name) {}
}
