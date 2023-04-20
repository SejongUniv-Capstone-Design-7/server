package capstone.capstone7.global.auth.service;

import capstone.capstone7.domain.member.entity.Member;
import capstone.capstone7.domain.member.repository.MemberRepository;
import capstone.capstone7.global.auth.entity.LoginUser;
import capstone.capstone7.global.error.exception.custom.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static capstone.capstone7.global.error.enums.ErrorMessage.NOT_EXIST_USER;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService {
    private final MemberRepository memberRepository;

    /** Email이 DB에 존재하는지 확인 **/
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new AuthException(NOT_EXIST_USER));

        /** 시큐리티 세션에 유저 정보 저장 **/
        return LoginUser.builder().member(member).build();
    }
}
