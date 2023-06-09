package capstone.capstone7.domain.like.service;

import capstone.capstone7.domain.member.entity.Member;
import capstone.capstone7.domain.member.repository.MemberRepository;
import capstone.capstone7.domain.board.entity.Board;
import capstone.capstone7.domain.board.repository.BoardRepository;
import capstone.capstone7.domain.like.dto.response.BoardLikeDeleteResponseDto;
import capstone.capstone7.domain.like.dto.response.BoardLikePostResponseDto;
import capstone.capstone7.domain.like.entity.BoardLike;
import capstone.capstone7.domain.like.repository.LikeRepository;
import capstone.capstone7.global.auth.entity.LoginUser;
import capstone.capstone7.global.error.enums.ErrorMessage;
import capstone.capstone7.global.error.exception.custom.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static capstone.capstone7.global.error.enums.ErrorMessage.ALREADY_DELETED_BOARDLIKE;
import static capstone.capstone7.global.error.enums.ErrorMessage.ALREADY_EXIST_BOARDLIKE;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public BoardLikePostResponseDto addLike(Long boardId, LoginUser loginUser){
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BusinessException(ErrorMessage.NOT_EXIST_BOARD));
        board.addLike();

        Member member = memberRepository.findById(loginUser.getMember().getId()).orElseThrow(() -> new BusinessException(ErrorMessage.NOT_EXIST_USER));

        // 이미 해당 게시물에 좋아요를 눌렀다면 에러처리
        if(likeRepository.existsByBoardAndMember(board, member)){
            throw new BusinessException(ALREADY_EXIST_BOARDLIKE);
        }
        BoardLike newBoardLike = BoardLike.builder()
                .board(board)
                .member(member)
                .build();

        likeRepository.save(newBoardLike);

        return new BoardLikePostResponseDto(board.getId(), board.getLikeNum());
    }

    public BoardLikeDeleteResponseDto deleteLike(Long boardId, LoginUser loginUser){
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BusinessException(ErrorMessage.NOT_EXIST_BOARD));
        board.minusLike();

        Member member = memberRepository.findById(loginUser.getMember().getId()).orElseThrow(() -> new BusinessException(ErrorMessage.NOT_EXIST_USER));

        // 이미 해당 게시물에 좋아요 취소를 눌렀다면 에러처리
        if(!likeRepository.existsByBoardAndMember(board, member)){
            throw new BusinessException(ALREADY_DELETED_BOARDLIKE);
        }

        BoardLike boardLike = likeRepository.findByBoardAndMember(board, member).orElseThrow(() -> new BusinessException(ALREADY_DELETED_BOARDLIKE));

        likeRepository.deleteById(boardLike.getId());

        return new BoardLikeDeleteResponseDto(board.getId(), board.getLikeNum());
    }
}
