package capstone.capstone7.global.config;

import capstone.capstone7.global.auth.filter.JwtAccessDeniedHandler;
import capstone.capstone7.global.auth.filter.JwtAuthenticationEntryPoint;
import capstone.capstone7.global.auth.filter.JwtAuthenticationFilter;
import capstone.capstone7.global.auth.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    // login을 위해서 permitAll하는 uri - permit uri
    private static final String[] SECURITY_PERMIT_URL_ARRAY = {
            "/auth/log-in",
            "/auth/sign-up",
            "/users/nickname/isDuplicated",
            "/users/token-reissue",
            "/diagnosis"
    };

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .formLogin().disable()
                .httpBasic().disable()
                .cors().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(SECURITY_PERMIT_URL_ARRAY).permitAll())
                //.anyRequest().authenticated()
                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                // 필터 예외
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler);

        return http.build();


    }

    // JWT 사용을 위해서는 기본적으로 password encoder 필요
    // Bycrypt encoder 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}