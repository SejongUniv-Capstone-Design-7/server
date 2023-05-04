package capstone.capstone7.domain.board.controller;

import capstone.capstone7.domain.board.dto.request.BoardCreateRequestDto;
import capstone.capstone7.domain.board.dto.request.BoardSolvedUpdateRequestDto;
import capstone.capstone7.domain.board.dto.request.BoardUpdateRequestDto;
import capstone.capstone7.domain.board.dto.response.*;
import capstone.capstone7.domain.board.entity.enums.Tag;
import capstone.capstone7.domain.board.service.BoardService;
import capstone.capstone7.global.auth.entity.LoginUser;
import capstone.capstone7.global.common.response.BaseResponseDto;
import capstone.capstone7.global.common.response.SliceResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
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
    public BaseResponseDto<BoardCreateResponseDto> createBoard(@Validated @RequestPart(value = "request") BoardCreateRequestDto boardCreateRequestDto, @RequestParam(value = "file", required = false) MultipartFile boardImage, @AuthenticationPrincipal LoginUser loginUser){
        return new BaseResponseDto<>(boardService.createBoard(boardImage, boardCreateRequestDto, loginUser.getMember()));
    }

    @GetMapping()
    public SliceResponseDto<GetBoardListResponseDto> getBoardsList(@PageableDefault(sort="createdDate",direction = Sort.Direction.DESC) Pageable pageable, @RequestParam Tag tag){ // 기본적으로는 수정일자(modifiedDate) 기준 오름차순 정렬
        return new SliceResponseDto<>(boardService.getBoardsList(tag, pageable));
    }

    @GetMapping("/{boardId}")
    public BaseResponseDto<GetBoardResponseDto> getBoard(@PathVariable Long boardId){
        return new BaseResponseDto<>(boardService.getBoard(boardId));
    }

    @PatchMapping(value = "/{boardId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResponseDto<BoardUpdateResponseDto> updateBoard(@PathVariable Long boardId, @Validated @RequestPart(value = "request") BoardUpdateRequestDto boardUpdateRequestDto, @RequestParam(value = "file", required = false) MultipartFile boardImage,  @AuthenticationPrincipal LoginUser loginUser){
        return new BaseResponseDto<>(boardService.updateBoard(loginUser.getMember(), boardId, boardImage, boardUpdateRequestDto));
    }

    @PatchMapping(value = "/{boardId}/solved",
            consumes = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResponseDto<BoardSolvedUpdateResponseDto> updateBoardSolved(@PathVariable Long boardId, @Validated @RequestBody BoardSolvedUpdateRequestDto boardSolvedUpdateRequestDto, @AuthenticationPrincipal LoginUser loginUser){
        return new BaseResponseDto<>(boardService.updateBoardSolved(boardId, boardSolvedUpdateRequestDto));
    }

    @DeleteMapping("/{boardId}")
    public BaseResponseDto<BoardDeleteResponseDto> deleteBoard(@PathVariable Long boardId){
        return new BaseResponseDto<>(boardService.deleteBoard(boardId));
    }
}
