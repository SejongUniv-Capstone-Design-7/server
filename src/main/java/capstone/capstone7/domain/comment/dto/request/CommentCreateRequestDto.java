package capstone.capstone7.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentCreateRequestDto {
    @NotBlank(message = "댓글 내용은 필수 입력 값입니다.")
    @Size(max=100, message = "댓글은 100자를 넘지 않아야합니다.")
    private String content;
    private Long parentId;// 부모 댓글인 경우에는 null, 자식 댓글인 경우에는 부모 댓글의 ID
}
