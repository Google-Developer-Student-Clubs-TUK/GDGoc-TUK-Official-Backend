package gdgoc.tuk.official.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.global.response.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response,
        final AuthenticationException exception) throws IOException {
        if(exception instanceof UsernameNotFoundException){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            objectMapper.writeValue(response.getWriter(), new ErrorResponse(ErrorCode.ACCOUNT_NOT_FOUND));
        }
        if(exception instanceof BadCredentialsException){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            objectMapper.writeValue(response.getWriter(), new ErrorResponse(ErrorCode.PASSWORD_NOT_MATCH));
        }
    }
}
