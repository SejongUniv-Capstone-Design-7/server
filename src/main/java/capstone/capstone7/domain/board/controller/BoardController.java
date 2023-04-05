package capstone.capstone7.domain.board.controller;

import capstone.capstone7.domain.board.dto.request.BoardCreateRequestDto;
import capstone.capstone7.domain.board.dto.response.BoardCreateResponseDto;
import capstone.capstone7.domain.board.service.BoardService;
import capstone.capstone7.global.auth.entity.LoginUser;
import capstone.capstone7.global.common.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/boards")
@RestController
public class BoardController {

    private final BoardService boardService;

    @PostMapping(value = "",
            consumes = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResponseDto<BoardCreateResponseDto> createBoard(@RequestPart(value = "request") BoardCreateRequestDto boardCreateRequestDto, @RequestParam(value = "file", required = false) MultipartFile boardImage, @AuthenticationPrincipal LoginUser loginUser){
        return new BaseResponseDto<>(boardService.createBoard(boardImage, boardCreateRequestDto, loginUser.getMember()));
    }
}
