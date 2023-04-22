package capstone.capstone7.global.auth.service;

import capstone.capstone7.domain.member.entity.Member;
import capstone.capstone7.domain.member.entity.enums.Region;
import capstone.capstone7.domain.member.repository.MemberRepository;
import capstone.capstone7.global.auth.dto.request.LoginRequestDto;
import capstone.capstone7.global.auth.dto.request.SignUpRequestDto;
import capstone.capstone7.global.auth.dto.response.LoginResponseDto;
import capstone.capstone7.global.auth.dto.response.SignUpResponseDto;
import capstone.capstone7.global.auth.entity.TokenInfo;
import capstone.capstone7.global.auth.jwt.TokenProvider;
import capstone.capstone7.global.error.exception.custom.AuthException;
import capstone.capstone7.global.error.exception.custom.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static capstone.capstone7.global.error.enums.ErrorMessage.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto){
        Region regionEnum = Region.getRegionEnumByKrName(signUpRequestDto.getRegion());
        if (regionEnum == null){
            throw new AuthException(WRONG_REGION);
        }

        if(memberRepository.existsByEmail(signUpRequestDto.getEmail())){
            throw new AuthException(DUPLICATED_USER);
        }

        Member newMember = Member.builder()
                .email(signUpRequestDto.getEmail())
                .password(signUpRequestDto.getPassword())
                .nickname(signUpRequestDto.getNickname())
                .region(regionEnum)
                .build();

        newMember.encodePassword(passwordEncoder);

        memberRepository.save(newMember);

        return new SignUpResponseDto(newMember.getId());
    }

    @Transactional(readOnly = true)
    public LoginResponseDto logIn(LoginRequestDto loginRequestDto){
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        Member member = memberRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(() -> new BusinessException(NOT_EXIST_USER));
        return new LoginResponseDto(tokenInfo, member.getId());
    }

}
