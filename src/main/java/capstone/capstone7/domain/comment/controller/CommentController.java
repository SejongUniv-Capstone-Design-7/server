package capstone.capstone7.domain.comment.controller;

import capstone.capstone7.domain.comment.dto.request.CommentCreateRequestDto;
import capstone.capstone7.domain.comment.dto.response.CommentCreateResponseDto;
import capstone.capstone7.domain.comment.service.CommentService;
import capstone.capstone7.global.auth.entity.LoginUser;
import capstone.capstone7.global.common.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/boards/{boardId}/comments")
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("")
    public BaseResponseDto<CommentCreateResponseDto> createComment(@PathVariable Long boardId, @RequestBody CommentCreateRequestDto commentCreateRequestDto, @AuthenticationPrincipal LoginUser loginUser){
        return new BaseResponseDto<>(commentService.createComment(boardId, commentCreateRequestDto, loginUser));
    }
}
