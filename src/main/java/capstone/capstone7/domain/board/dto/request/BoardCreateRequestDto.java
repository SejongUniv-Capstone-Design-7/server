package capstone.capstone7.domain.board.dto.request;

import capstone.capstone7.domain.board.entity.enums.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class BoardCreateRequestDto {
    @NotBlank(message = "게시글 제목은 필수 입력 값입니다.")
    @Size(max=30, message = "게시글 제목은 30자 이하여야합니다.")
    private String title;

    @NotBlank(message = "게시글 내용은 필수 입력 값입니다.")
    @Size(max=500, message = "게시글 내용은 500자 이하여야합니다.")
    private String content;

    @NotNull(message = "태그는 KNOWHOW와 QUESTION 중에서 하나를 필수로 입력해야합니다.")
    private Tag tag;
}
