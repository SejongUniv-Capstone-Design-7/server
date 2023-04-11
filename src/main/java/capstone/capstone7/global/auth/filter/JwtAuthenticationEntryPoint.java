package capstone.capstone7.global.auth.filter;

import capstone.capstone7.global.common.response.BaseResponseDto;
import capstone.capstone7.global.error.enums.ErrorMessage;
import capstone.capstone7.global.error.exception.custom.AuthException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static capstone.capstone7.global.error.enums.ErrorMessage.FORBIDDEN;
import static capstone.capstone7.global.error.enums.ErrorMessage.UNAUTHORIZED;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401(인증 실패)
        log.info("JwtAuthenticationEntryPoint");
        throw new AuthException(UNAUTHORIZED);
    }
}
