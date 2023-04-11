package capstone.capstone7.global.auth.filter;

import capstone.capstone7.global.error.exception.custom.AuthException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static capstone.capstone7.global.error.enums.ErrorMessage.FORBIDDEN;

@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //필요한 권한이 없이 접근하려 할때 403
        log.info("AccessDeniedHandler: 403 에러 호출");
        throw new AuthException(FORBIDDEN);
    }
}
