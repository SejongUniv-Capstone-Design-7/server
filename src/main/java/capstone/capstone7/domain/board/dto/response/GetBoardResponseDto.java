package capstone.capstone7.domain.board.dto.response;

import capstone.capstone7.domain.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetBoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private String tag;
    private String image;
    private Boolean isSolved;
    private Long memberId;

    public GetBoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.tag = board.getTag().toString();
        this.image = board.getImage();
        this.isSolved = board.getIsSolved();
        this.memberId = board.getMember().getId();
    }
}
