package capstone.capstone7.domain.comment.service;

import capstone.capstone7.domain.board.entity.Board;
import capstone.capstone7.domain.board.repository.BoardRepository;
import capstone.capstone7.domain.comment.dto.request.CommentCreateRequestDto;
import capstone.capstone7.domain.comment.dto.request.CommentPatchRequestDto;
import capstone.capstone7.domain.comment.dto.response.CommentCreateResponseDto;
import capstone.capstone7.domain.comment.dto.response.CommentDeleteResponseDto;
import capstone.capstone7.domain.comment.dto.response.CommentGetResponseDto;
import capstone.capstone7.domain.comment.dto.response.CommentPatchResponseDto;
import capstone.capstone7.domain.comment.entity.Comment;
import capstone.capstone7.domain.comment.repository.CommentRepository;
import capstone.capstone7.domain.member.entity.Member;
import capstone.capstone7.domain.member.repository.MemberRepository;
import capstone.capstone7.global.auth.entity.LoginUser;
import capstone.capstone7.global.error.exception.custom.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static capstone.capstone7.global.error.enums.ErrorMessage.*;

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

    public CommentPatchResponseDto updateComment(Long boardId, CommentPatchRequestDto commentPatchRequestDto, Long commentId, LoginUser loginUser) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new BusinessException(NOT_EXIST_COMMENT));
        // Question : 댓글 작성자가 로그인 한 유저와 같은지 비교할 때, comment.getMember() == loginUser.getMember()와 같이 작성하면 false라는 값이 나온다.
        // 같은 영속성 컨텍스트에서 꺼낸 Member 엔티티는 주소 값이 같다. 그러나 로그인으로 얻어온 Member 엔티티는 주소값이 다르다.
        // log.info("comment.getMember() == loginUser.getMember() : {}", comment.getMember() == loginUser.getMember()); // false
        // log.info("loginUser.getMember() : {}", loginUser.getMember()); // capstone.capstone7.domain.Member.entity.Member@26dc1fd3
        // log.info("comment.getMember() : {}", comment.getMember()); // capstone.capstone7.domain.Member.entity.Member@76184ae3

        // Member findMember = memberRepository.findById(loginUser.getMember().getId()).get();
        // log.info("findMember : {}", findMember); // capstone.capstone7.domain.Member.entity.Member@76184ae3

        if(comment.getMember().getId() != loginUser.getMember().getId()){ // 댓글 작성자가 로그인한 유저와 같지 않다면
            throw new BusinessException(INVALID_USER);
        }else if(comment.getBoard().getId() != boardId){ // path variable로 받아온 게시글 Id와 댓글의 게시글 Id가 같지 않다면
            throw new BusinessException(NOT_EXIST_COMMENT);
        }else{
            comment.updateComment(commentPatchRequestDto.getContent());
        }

        return new CommentPatchResponseDto(comment.getId());
    }

    @Transactional(readOnly = true)
    public List<CommentGetResponseDto> getAllComments(Long boardId, Pageable pageable){
        return commentRepository.findAllComment(boardId, pageable);
    }

    public CommentDeleteResponseDto deleteComment(Long boardId, Long commentId, LoginUser loginUser) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new BusinessException(NOT_EXIST_COMMENT));

        if(comment.getMember().getId() != loginUser.getMember().getId()){ // 댓글 작성자가 로그인한 유저와 같지 않다면
            throw new BusinessException(INVALID_USER);
        }else if(comment.getBoard().getId() != boardId){ // path variable로 받아온 게시글 Id와 댓글의 게시글 Id가 같지 않다면
            throw new BusinessException(NOT_EXIST_COMMENT);
        }else{
            commentRepository.deleteById(commentId);
        }

        return new CommentDeleteResponseDto(comment.getId());
    }
}
