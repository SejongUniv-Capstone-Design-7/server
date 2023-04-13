package capstone.capstone7.domain.like.controller;

import capstone.capstone7.domain.like.dto.response.BoardLikeDeleteResponseDto;
import capstone.capstone7.domain.like.dto.response.BoardLikePostResponseDto;
import capstone.capstone7.domain.like.service.LikeService;
import capstone.capstone7.global.auth.entity.LoginUser;
import capstone.capstone7.global.common.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/boards/{boardId}/like")
@RestController
public class LikeController {
    private final LikeService likeService;

    @PostMapping("")
    public BaseResponseDto<BoardLikePostResponseDto> addLike(@PathVariable Long boardId, @AuthenticationPrincipal LoginUser loginUser){
        return new BaseResponseDto<>(likeService.addLike(boardId, loginUser));
    }

    @DeleteMapping("")
    public BaseResponseDto<BoardLikeDeleteResponseDto> deleteLike(@PathVariable Long boardId, @AuthenticationPrincipal LoginUser loginUser){
        return new BaseResponseDto<>(likeService.deleteLike(boardId, loginUser));
    }
}
