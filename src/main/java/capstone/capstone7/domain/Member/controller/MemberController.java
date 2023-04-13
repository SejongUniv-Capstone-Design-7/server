package capstone.capstone7.domain.Member.controller;

import capstone.capstone7.domain.Member.dto.response.MemberGetResponseDto;
import capstone.capstone7.domain.Member.service.MemberService;
import capstone.capstone7.global.auth.entity.LoginUser;
import capstone.capstone7.global.common.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/members/{memberId}")
@RestController
public class MemberController {

    private final MemberService memberService;
    @GetMapping("")
    public BaseResponseDto<MemberGetResponseDto> getMemberInfo(@PathVariable Long memberId, @AuthenticationPrincipal LoginUser loginUser){
        return new BaseResponseDto<>(memberService.getMemberInfo(memberId, loginUser));
    }
}
