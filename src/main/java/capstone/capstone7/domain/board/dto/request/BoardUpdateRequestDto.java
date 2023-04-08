package capstone.capstone7.domain.board.dto.request;

import capstone.capstone7.domain.board.entity.enums.Tag;
import lombok.Getter;

@Getter
public class BoardUpdateRequestDto {
    private String title;
    private String content;
    private Tag tag;
}
