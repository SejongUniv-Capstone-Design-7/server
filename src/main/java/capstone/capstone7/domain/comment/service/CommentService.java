package capstone.capstone7.domain.comment.service;

import capstone.capstone7.domain.Member.entity.Member;
import capstone.capstone7.domain.Member.repository.MemberRepository;
import capstone.capstone7.domain.board.entity.Board;
import capstone.capstone7.domain.board.repository.BoardRepository;
import capstone.capstone7.domain.comment.dto.request.CommentCreateRequestDto;
import capstone.capstone7.domain.comment.dto.response.CommentCreateResponseDto;
import capstone.capstone7.domain.comment.entity.Comment;
import capstone.capstone7.domain.comment.repository.CommentRepository;
import capstone.capstone7.global.auth.entity.LoginUser;
import capstone.capstone7.global.error.exception.custom.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static capstone.capstone7.global.error.enums.ErrorMessage.NOT_EXIST_BOARD;
import static capstone.capstone7.global.error.enums.ErrorMessage.NOT_EXIST_USER;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public CommentCreateResponseDto createComment(Long boardId, CommentCreateRequestDto commentCreateRequestDto, LoginUser loginUser){
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BusinessException(NOT_EXIST_BOARD));

        Member member = memberRepository.findById(loginUser.getMember().getId()).orElseThrow(() -> new BusinessException(NOT_EXIST_USER));

        Comment newComment = Comment.builder()
                .content(commentCreateRequestDto.getContent())
                .board(board)
                .member(member)
                .build();

        commentRepository.save(newComment);

        return new CommentCreateResponseDto(newComment.getId());
    }
}
