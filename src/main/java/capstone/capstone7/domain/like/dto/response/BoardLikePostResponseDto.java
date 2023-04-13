package capstone.capstone7.domain.like.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardLikePostResponseDto {
    private Long boardId;
    private Integer likeNum;
}
