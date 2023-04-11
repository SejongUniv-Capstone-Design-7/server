package capstone.capstone7.global.auth.filter;

import capstone.capstone7.global.common.response.BaseResponseDto;
import capstone.capstone7.global.error.enums.ErrorMessage;
import capstone.capstone7.global.error.exception.custom.AuthException;
import capstone.capstone7.global.error.exception.custom.InvalidTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class FilterExceptionHandler extends OncePerRequestFilter {
    ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
           filterChain.doFilter(request, response);
        }catch(InvalidTokenException | AuthException e){
            log.info("FilterExceptionHandler {}", e.getErrorMessage());
            response.setStatus(e.getErrorMessage().getCode());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            objectMapper.writeValue(response.getWriter(), new BaseResponseDto<>(e.getErrorMessage()));
        }
    }
}
