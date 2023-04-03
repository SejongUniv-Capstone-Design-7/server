package capstone.capstone7.global.auth.service;

import capstone.capstone7.domain.Member.entity.Member;
import capstone.capstone7.domain.Member.repository.MemberRepository;
import capstone.capstone7.global.auth.entity.LoginUser;
import capstone.capstone7.global.error.exception.custom.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static capstone.capstone7.global.error.enums.ErrorMessage.WRONG_EMAIL;

@Service
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService {
    private final MemberRepository memberRepository;

    /** Email이 DB에 존재하는지 확인 **/
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));

        /** 시큐리티 세션에 유저 정보 저장 **/
        return LoginUser.builder().member(member).build();
    }
}
