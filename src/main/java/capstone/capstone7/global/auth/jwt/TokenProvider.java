package capstone.capstone7.global.auth.jwt;

import capstone.capstone7.global.auth.entity.TokenInfo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class TokenProvider {
    @Value("${spring.security.jwt.header}")
    private String AUTHORIZATION_HEADER;

    @Value("${spring.security.jwt.secret}")
    private String secretKey;

    private long ACCESS_TOKEN_EXPIRE_TIME = Duration.ofMinutes(30).toMillis(); // 만료시간 30분

    private long REFRESH_TOKEN_EXPIRE_TIME = Duration.ofDays(14).toMillis(); // 만료시간 2주

    private final UserDetailsService userDetailsService;

    private Key getSigninKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }


    // 유저 정보를 가지고 JWT(AccessToken, RefreshToken)을 생성하는 메서드
    public TokenInfo generateToken(Authentication authentication) {
        // 현재 시각
        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())// email
                .setExpiration(accessTokenExpiresIn)
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        Date refreshTokenExpiresIn = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);
        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())// email
                .setExpiration(refreshTokenExpiresIn)
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();

        // GrantType, AccessToken, RefreshToken을 담은 TokenInfo 객체 반환
        return TokenInfo.builder()
                .grantType(AUTHORIZATION_HEADER)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     *
     * @param token
     * @return 토큰에서 유저 이메일 획득
     */
    public String getUserEmail(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException e) { // 만료된 토큰이더라도 일단 파싱을 함
            return e.getClaims().getSubject(); // 만료된 토큰이더라도 반환하는 이유는 재발행 때문
        }
    }

    /**
     *
     * @param token
     * @return 토큰의 유효성 + 만료일자 확인
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException | DecodingException e) {
            log.info("잘못된 JWT 서명입니다.");
            // throw new InvalidTokenException(ErrorMessage.WRONG_JWT_SIGNITURE);
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            //throw new InvalidTokenException(ErrorMessage.EXPIRED_JWT_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            //throw new InvalidTokenException(ErrorMessage.NOT_APPLY_JWT_TOKEN);
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            //throw new InvalidTokenException(ErrorMessage.WRONG_JWT_TOKEN);
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
