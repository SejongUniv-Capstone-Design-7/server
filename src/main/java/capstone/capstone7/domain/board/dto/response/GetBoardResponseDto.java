package capstone.capstone7.domain.board.dto.response;

import capstone.capstone7.domain.board.entity.Board;
import capstone.capstone7.domain.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class GetBoardResponseDto {
    private Long boardId;
    private String title;
    private String content;
    private String tag;
    private String image;
    private Boolean isSolved;
    private Long memberId;
    private String nickname;
    private Integer likeNum;
    private Integer commentNum;
    private List<Long> likeMemberIds; // 해당 게시물 좋아요 누른 사용자ID
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    public GetBoardResponseDto(Board board, Member member, Integer commentNum, List<Long> likeMemberIds) {
        this.boardId = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.tag = board.getTag().toString();
        this.image = board.getImage();
        this.isSolved = board.getIsSolved();
        this.memberId = board.getMember().getId();
        this.createdDate = board.getCreatedDate();
        this.nickname = member.getNickname();
        this.likeNum = board.getLikeNum();
        this.commentNum = commentNum;
        this.likeMemberIds = likeMemberIds;
    }
}
