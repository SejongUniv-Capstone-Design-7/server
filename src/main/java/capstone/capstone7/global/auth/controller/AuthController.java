package capstone.capstone7.global.auth.controller;

import capstone.capstone7.global.auth.dto.request.LoginRequestDto;
import capstone.capstone7.global.auth.dto.request.SignUpRequestDto;
import capstone.capstone7.global.auth.dto.response.LoginResponseDto;
import capstone.capstone7.global.auth.dto.response.SignUpResponseDto;
import capstone.capstone7.global.auth.service.AuthService;
import capstone.capstone7.global.common.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public BaseResponseDto<SignUpResponseDto> signUp(@Validated @RequestBody SignUpRequestDto signUpRequestDto){
        return new BaseResponseDto<>(authService.signUp(signUpRequestDto));
    }

    @PostMapping("/log-in")
    public BaseResponseDto<LoginResponseDto> logIn(@Validated @RequestBody LoginRequestDto loginRequestDto){
        return new BaseResponseDto<>(authService.logIn(loginRequestDto));
    }

}
