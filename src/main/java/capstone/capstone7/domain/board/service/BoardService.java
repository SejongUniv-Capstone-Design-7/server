package capstone.capstone7.domain.board.service;

import capstone.capstone7.domain.board.dto.request.BoardCreateRequestDto;
import capstone.capstone7.domain.board.dto.request.BoardSolvedUpdateRequestDto;
import capstone.capstone7.domain.board.dto.request.BoardUpdateRequestDto;
import capstone.capstone7.domain.board.dto.response.*;
import capstone.capstone7.domain.board.entity.Board;
import capstone.capstone7.domain.board.entity.enums.Tag;
import capstone.capstone7.domain.board.repository.BoardRepository;
import capstone.capstone7.domain.comment.repository.CommentRepository;
import capstone.capstone7.domain.like.repository.LikeRepository;
import capstone.capstone7.domain.member.entity.Member;
import capstone.capstone7.domain.member.repository.MemberRepository;
import capstone.capstone7.global.S3.FileService;
import capstone.capstone7.global.error.exception.custom.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static capstone.capstone7.global.error.enums.ErrorMessage.NOT_EXIST_BOARD;
import static capstone.capstone7.global.error.enums.ErrorMessage.NOT_EXIST_USER;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    private final FileService fileService;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

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

    public Slice<GetBoardListResponseDto> getBoardsList(Tag tag, Pageable pageable){

        Slice<Board> boardSlice = boardRepository.findBoardListByTag(tag, pageable);
        Slice<GetBoardListResponseDto> boardSliceDto = boardSlice.map(board -> {
            Member member = memberRepository.findById(board.getMember().getId()).orElseThrow(() -> new BusinessException(NOT_EXIST_USER));
            return new GetBoardListResponseDto(board, member, commentRepository.countByBoard(board));
        });
        return boardSliceDto;
    }

    public GetBoardResponseDto getBoard(Long boardId){
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BusinessException(NOT_EXIST_BOARD));
        Member member = memberRepository.findById(board.getMember().getId()).orElseThrow(() -> new BusinessException(NOT_EXIST_USER));
        return new GetBoardResponseDto(board, member, commentRepository.countByBoard(board), likeRepository.findLikeMemberIdsByBoard(board));
    }

    @Transactional
    public BoardUpdateResponseDto updateBoard(Member member, Long boardId, MultipartFile boardImage, BoardUpdateRequestDto boardUpdateRequestDto){

        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BusinessException(NOT_EXIST_BOARD));
        if(board.getImage()!=null){
            fileService.deleteFile(board.getImage());
        }
        String savedFilePath = fileService.uploadFileToS3(boardImage, member.getId());
        board.boardContentUpdate(savedFilePath, boardUpdateRequestDto);

        return new BoardUpdateResponseDto(board.getId());
    }

    @Transactional
    public BoardSolvedUpdateResponseDto updateBoardSolved(Long boardId, BoardSolvedUpdateRequestDto boardSolvedUpdateRequestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BusinessException(NOT_EXIST_BOARD));
        board.boardSolvedUpdate(boardSolvedUpdateRequestDto.getIsSolved());
        return new BoardSolvedUpdateResponseDto(board.getId());
    }

    @Transactional
    public BoardDeleteResponseDto deleteBoard(Long boardId){
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BusinessException(NOT_EXIST_BOARD));
        if (board.getImage() != null){
            fileService.deleteFile(board.getImage());
        }

        likeRepository.deleteAllInBatch(likeRepository.findAllLikeByBoard(board));
        commentRepository.deleteAllInBatch(commentRepository.findAllCommentByBoard(board));
        boardRepository.deleteById(boardId); // 해당 boardId를 가진 Board가 없다면, delete 요청 무시
        return new BoardDeleteResponseDto(boardId);
    }

    public List<GetBoardListResponseDto> getBestBoards() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);

        List<Board> top3By = boardRepository.findTop3ByLikeNumDesc(oneWeekAgo, today);
        List<GetBoardListResponseDto> getBoardListResponseDtos = top3By.stream().map(board -> {
            Member member = memberRepository.findById(board.getMember().getId()).orElseThrow(() -> new BusinessException(NOT_EXIST_USER));
            return new GetBoardListResponseDto(board, member, commentRepository.countByBoard(board));
        }).toList();
        return getBoardListResponseDtos;
    }
}
