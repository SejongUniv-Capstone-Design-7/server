package capstone.capstone7.global.auth;

import capstone.capstone7.domain.Member.entity.Member;
import capstone.capstone7.domain.Member.entity.enums.Region;
import capstone.capstone7.domain.Member.repository.MemberRepository;
import capstone.capstone7.global.auth.dto.SignUpRequestDto;
import capstone.capstone7.global.auth.dto.SignUpResponseDto;
import capstone.capstone7.global.error.exception.custom.AuthException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static capstone.capstone7.global.error.enums.ErrorMessage.WRONG_REGION;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {
    private final MemberRepository memberRepository;

    @Transactional
    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto){
        Region regionEnum = Region.getRegionEnumByKrName(signUpRequestDto.getRegion());
        if (regionEnum == null){
            throw new AuthException(WRONG_REGION);
        }

        Member newMember = Member.builder()
                .email(signUpRequestDto.getEmail())
                .password(signUpRequestDto.getPassword())
                .nickname(signUpRequestDto.getNickname())
                .region(regionEnum)
                .build();

        memberRepository.save(newMember);

        return new SignUpResponseDto(newMember.getId());
    }
}
