package capstone.capstone7.domain.comment.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ParentCommentGetResponseDto {
    private Long commentId;
    private String content;

    private Long memberId;
    private String nickname;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    private List<ChildCommentGetResponseDto> childComments;

    public void updateChildComments(List<ChildCommentGetResponseDto> childComments){
        this.childComments = childComments;
    }

    public ParentCommentGetResponseDto(Long commentId, String content, Long memberId, String nickname, LocalDateTime createdDate) {
        this.commentId = commentId;
        this.content = content;
        this.memberId = memberId;
        this.nickname = nickname;
        this.createdDate = createdDate;
    }
}
