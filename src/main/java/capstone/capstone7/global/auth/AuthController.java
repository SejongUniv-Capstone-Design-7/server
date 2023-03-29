package capstone.capstone7.global.auth;

import capstone.capstone7.global.auth.dto.SignUpRequestDto;
import capstone.capstone7.global.auth.dto.SignUpResponseDto;
import capstone.capstone7.global.common.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
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

}
