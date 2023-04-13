package capstone.capstone7.domain.Member.service;

import capstone.capstone7.domain.Member.dto.response.MemberGetResponseDto;
import capstone.capstone7.domain.Member.entity.Member;
import capstone.capstone7.domain.Member.repository.MemberRepository;
import capstone.capstone7.global.auth.entity.LoginUser;
import capstone.capstone7.global.error.exception.custom.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static capstone.capstone7.global.error.enums.ErrorMessage.INVALID_USER;
import static capstone.capstone7.global.error.enums.ErrorMessage.NOT_EXIST_USER;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberGetResponseDto getMemberInfo(Long memberId, LoginUser loginUser){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(NOT_EXIST_USER));

        if(member.getId() != loginUser.getMember().getId()){
            throw new BusinessException(INVALID_USER);
        }

        return new MemberGetResponseDto(member.getId(), member.getEmail(), member.getNickname(), member.getRegion().getKoreanName());
    }
}
