package capstone.capstone7.domain.board.service;

import capstone.capstone7.domain.Member.entity.Member;
import capstone.capstone7.domain.board.dto.request.BoardCreateRequestDto;
import capstone.capstone7.domain.board.dto.request.BoardUpdateRequestDto;
import capstone.capstone7.domain.board.dto.response.BoardCreateResponseDto;
import capstone.capstone7.domain.board.dto.response.BoardDeleteResponseDto;
import capstone.capstone7.domain.board.dto.response.BoardUpdateResponseDto;
import capstone.capstone7.domain.board.dto.response.GetBoardResponseDto;
import capstone.capstone7.domain.board.entity.Board;
import capstone.capstone7.domain.board.repository.BoardRepository;
import capstone.capstone7.global.S3.FileService;
import capstone.capstone7.global.error.exception.custom.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static capstone.capstone7.global.error.enums.ErrorMessage.NOT_EXIST_BOARD;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    private final FileService fileService;

    @Transactional
    public BoardCreateResponseDto createBoard(MultipartFile boardImage, BoardCreateRequestDto boardCreateRequestDto, Member member){
        String savedFilePath = fileService.uploadFileToS3(boardImage, member.getId());

        Board newBoard = Board.builder()
                .title(boardCreateRequestDto.getTitle())
                .content(boardCreateRequestDto.getContent())
                .tag(boardCreateRequestDto.getTag())
                .image(savedFilePath)
                .member(member)
                .build();

        boardRepository.save(newBoard);
        return new BoardCreateResponseDto(newBoard.getId());
    }

    public Slice<GetBoardResponseDto> getBoardsList(Pageable pageable){
        Slice<Board> boardSlice = boardRepository.findBoardListBy(pageable);
        Slice<GetBoardResponseDto> boardSliceDto = boardSlice.map(GetBoardResponseDto::new);
        return boardSliceDto;
    }

    public GetBoardResponseDto getBoard(Long boardId){
        return new GetBoardResponseDto(boardRepository.findById(boardId).orElseThrow(() -> new BusinessException(NOT_EXIST_BOARD)));
    }

    @Transactional
    public BoardUpdateResponseDto updateBoard(Member member, Long boardId, MultipartFile boardImage, BoardUpdateRequestDto boardUpdateRequestDto){

        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BusinessException(NOT_EXIST_BOARD));

        String savedFilePath = fileService.uploadFileToS3(boardImage, member.getId());
        board.boardContentUpdate(savedFilePath, boardUpdateRequestDto);

        return new BoardUpdateResponseDto(board.getId());
    }

    @Transactional
    public BoardDeleteResponseDto deleteBoard(Long boardId){
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BusinessException(NOT_EXIST_BOARD));
        if (board.getImage() != null){
            fileService.deleteFile(board.getImage());
        }
        boardRepository.deleteById(boardId); // 해당 boardId를 가진 Board가 없다면, delete 요청 무시
        return new BoardDeleteResponseDto(boardId);
    }
}
