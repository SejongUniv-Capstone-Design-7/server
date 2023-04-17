package capstone.capstone7.domain.board.dto.response;

import capstone.capstone7.domain.member.entity.Member;
import capstone.capstone7.domain.board.entity.Board;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedDate;

    public GetBoardResponseDto(Board board, Member member) {
        this.boardId = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.tag = board.getTag().toString();
        this.image = board.getImage();
        this.isSolved = board.getIsSolved();
        this.memberId = board.getMember().getId();
        this.modifiedDate = board.getModifiedDate();
        this.nickname = member.getNickname();
        this.likeNum = board.getLikeNum();
    }
}
