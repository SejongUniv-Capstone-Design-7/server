package capstone.capstone7.domain.board.service;

import capstone.capstone7.domain.Member.entity.Member;
import capstone.capstone7.domain.board.dto.request.BoardCreateRequestDto;
import capstone.capstone7.domain.board.dto.response.BoardCreateResponseDto;
import capstone.capstone7.domain.board.entity.Board;
import capstone.capstone7.domain.board.repository.BoardRepository;
import capstone.capstone7.global.S3.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    private final FileService fileService;
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
}
