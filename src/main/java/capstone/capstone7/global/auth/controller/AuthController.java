package capstone.capstone7.global.auth.controller;

import capstone.capstone7.global.auth.dto.LoginRequestDto;
import capstone.capstone7.global.auth.dto.LoginResponseDto;
import capstone.capstone7.global.auth.dto.SignUpRequestDto;
import capstone.capstone7.global.auth.dto.SignUpResponseDto;
import capstone.capstone7.global.auth.service.AuthService;
import capstone.capstone7.global.common.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public BaseResponseDto<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        return new BaseResponseDto<>(authService.signUp(signUpRequestDto));
    }

    @PostMapping("/log-in")
    public BaseResponseDto<LoginResponseDto> logIn(@RequestBody LoginRequestDto loginRequestDto){
        return new BaseResponseDto<>(authService.logIn(loginRequestDto));
    }

}
