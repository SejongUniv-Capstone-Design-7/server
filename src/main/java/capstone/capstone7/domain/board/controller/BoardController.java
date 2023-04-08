package capstone.capstone7.domain.board.controller;

import capstone.capstone7.domain.board.dto.request.BoardCreateRequestDto;
import capstone.capstone7.domain.board.dto.request.BoardUpdateRequestDto;
import capstone.capstone7.domain.board.dto.response.BoardCreateResponseDto;
import capstone.capstone7.domain.board.dto.response.BoardDeleteResponseDto;
import capstone.capstone7.domain.board.dto.response.BoardUpdateResponseDto;
import capstone.capstone7.domain.board.dto.response.GetBoardResponseDto;
import capstone.capstone7.domain.board.service.BoardService;
import capstone.capstone7.global.auth.entity.LoginUser;
import capstone.capstone7.global.common.response.BaseResponseDto;
import capstone.capstone7.global.common.response.SliceResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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

    @GetMapping()
    public SliceResponseDto<GetBoardResponseDto> getBoardsList(Pageable pageable){
        return new SliceResponseDto<>(boardService.getBoardsList(pageable));
    }

    @GetMapping("/{boardId}")
    public BaseResponseDto<GetBoardResponseDto> getBoard(@PathVariable Long boardId){
        return new BaseResponseDto<>(boardService.getBoard(boardId));
    }

    @PatchMapping(value = "/{boardId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResponseDto<BoardUpdateResponseDto> updateBoard(@PathVariable Long boardId, @RequestPart(value = "request") BoardUpdateRequestDto boardUpdateRequestDto, @RequestParam(value = "file", required = false) MultipartFile boardImage,  @AuthenticationPrincipal LoginUser loginUser){
        return new BaseResponseDto<>(boardService.updateBoard(loginUser.getMember(), boardId, boardImage, boardUpdateRequestDto));
    }

    @DeleteMapping("/{boardId}")
    public BaseResponseDto<BoardDeleteResponseDto> deleteBoard(@PathVariable Long boardId){
        return new BaseResponseDto<>(boardService.deleteBoard(boardId));
    }
}
