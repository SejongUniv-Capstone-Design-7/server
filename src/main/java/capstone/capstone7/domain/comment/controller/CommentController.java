package capstone.capstone7.domain.comment.controller;

import capstone.capstone7.domain.comment.dto.request.CommentCreateRequestDto;
import capstone.capstone7.domain.comment.dto.request.CommentPatchRequestDto;
import capstone.capstone7.domain.comment.dto.response.CommentCreateResponseDto;
import capstone.capstone7.domain.comment.dto.response.CommentDeleteResponseDto;
import capstone.capstone7.domain.comment.dto.response.CommentGetResponseDto;
import capstone.capstone7.domain.comment.dto.response.CommentPatchResponseDto;
import capstone.capstone7.domain.comment.service.CommentService;
import capstone.capstone7.global.auth.entity.LoginUser;
import capstone.capstone7.global.common.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PatchMapping("/{commentId}")
    public BaseResponseDto<CommentPatchResponseDto> createComment(@PathVariable Long boardId, @RequestBody CommentPatchRequestDto commentPatchRequestDto, @PathVariable Long commentId, @AuthenticationPrincipal LoginUser loginUser){
        return new BaseResponseDto<>(commentService.updateComment(boardId, commentPatchRequestDto, commentId, loginUser));
    }

    @GetMapping("")
    public BaseResponseDto<List<CommentGetResponseDto>> getAllComments(@PathVariable Long boardId, @PageableDefault(sort="createdDate", direction = Sort.Direction.ASC)Pageable pageable){
        return new BaseResponseDto<>(commentService.getAllComments(boardId, pageable));
    }

    @DeleteMapping("/{commentId}")
    public BaseResponseDto<CommentDeleteResponseDto> createComment(@PathVariable Long boardId, @PathVariable Long commentId, @AuthenticationPrincipal LoginUser loginUser){
        return new BaseResponseDto<>(commentService.deleteComment(boardId, commentId, loginUser));
    }
}
