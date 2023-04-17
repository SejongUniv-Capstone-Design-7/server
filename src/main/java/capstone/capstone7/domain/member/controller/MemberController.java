package capstone.capstone7.domain.member.controller;

import capstone.capstone7.domain.member.dto.request.MemberPatchRequestDto;
import capstone.capstone7.domain.member.dto.response.MemberDeleteResponseDto;
import capstone.capstone7.domain.member.dto.response.MemberGetResponseDto;
import capstone.capstone7.domain.member.dto.response.MemberPatchResponseDto;
import capstone.capstone7.domain.member.service.MemberService;
import capstone.capstone7.global.auth.entity.LoginUser;
import capstone.capstone7.global.common.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/members/{memberId}")
@RestController
public class MemberController {

    private final MemberService memberService;
    @GetMapping("")
    public BaseResponseDto<MemberGetResponseDto> getMemberInfo(@PathVariable Long memberId, @AuthenticationPrincipal LoginUser loginUser){
        return new BaseResponseDto<>(memberService.getMemberInfo(memberId, loginUser));
    }

    @PatchMapping("")
    public BaseResponseDto<MemberPatchResponseDto> updateMember(@PathVariable Long memberId, @RequestBody MemberPatchRequestDto memberPatchRequestDto, @AuthenticationPrincipal LoginUser loginUser){
        return new BaseResponseDto<>(memberService.updateMember(memberId, memberPatchRequestDto, loginUser));
    }

    @DeleteMapping("")
    public BaseResponseDto<MemberDeleteResponseDto> deleteMember(@PathVariable Long memberId, @AuthenticationPrincipal LoginUser loginUser){
        return new BaseResponseDto<>(memberService.deleteMember(memberId, loginUser));
    }
}
