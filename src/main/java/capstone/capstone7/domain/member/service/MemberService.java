package capstone.capstone7.domain.member.service;

import capstone.capstone7.domain.board.entity.Board;
import capstone.capstone7.domain.board.repository.BoardRepository;
import capstone.capstone7.domain.board.service.BoardService;
import capstone.capstone7.domain.member.dto.request.MemberPatchRequestDto;
import capstone.capstone7.domain.member.dto.response.MemberDeleteResponseDto;
import capstone.capstone7.domain.member.dto.response.MemberGetResponseDto;
import capstone.capstone7.domain.member.dto.response.MemberPatchResponseDto;
import capstone.capstone7.domain.member.entity.Member;
import capstone.capstone7.domain.member.repository.MemberRepository;
import capstone.capstone7.global.auth.entity.LoginUser;
import capstone.capstone7.global.error.exception.custom.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static capstone.capstone7.global.error.enums.ErrorMessage.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BoardService boardService;
    private final BoardRepository boardRepository;

    public MemberGetResponseDto getMemberInfo(Long memberId, LoginUser loginUser){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(NOT_EXIST_USER));

        if(member.getId() != loginUser.getMember().getId()){
            throw new BusinessException(INVALID_USER);
        }

        return new MemberGetResponseDto(member.getId(), member.getEmail(), member.getNickname(), member.getRegion().getKoreanName());
    }

    @Transactional
    public MemberPatchResponseDto updateMember(Long memberId, MemberPatchRequestDto memberPatchRequestDto, LoginUser loginUser){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(NOT_EXIST_USER));

        if(member.getId() != loginUser.getMember().getId()){
            throw new BusinessException(INVALID_USER);
        }

        if(memberRepository.existsByNickname(memberPatchRequestDto.getNickname())){
            throw new BusinessException(DUPLICATED_NICKNAME);
        }

        member.update(memberPatchRequestDto.getNickname(), memberPatchRequestDto.getRegion());

        return new MemberPatchResponseDto(member.getNickname(), member.getRegion().getKoreanName());
    }

    @Transactional
    public MemberDeleteResponseDto deleteMember(Long memberId, LoginUser loginUser) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(NOT_EXIST_USER));

        if(member.getId() != loginUser.getMember().getId()){
            throw new BusinessException(INVALID_USER);
        }

        // 회원이 작성한 모든 게시물 삭제 및, 해당 게시물과 연관된 댓글, 좋아요 삭제
        List<Board> allBoardByMember = boardRepository.findAllBoardByMember(member);
        for (Board board : allBoardByMember) {
            boardService.deleteBoard(board.getId());
        }

        // 회원 삭제
        memberRepository.deleteById(memberId);

        return new MemberDeleteResponseDto(member.getId());
    }
}
