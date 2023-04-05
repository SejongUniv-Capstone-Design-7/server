package capstone.capstone7.domain.board.dto.request;

import capstone.capstone7.domain.board.entity.enums.Tag;
import lombok.Getter;

@Getter
public class BoardCreateRequestDto {
    private String title;
    private String content;
    private Tag tag; // 이게 가능한가
}
