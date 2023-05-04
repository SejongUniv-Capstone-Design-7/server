package capstone.capstone7.domain.comment.entity;

import capstone.capstone7.domain.board.entity.Board;
import capstone.capstone7.domain.member.entity.Member;
import capstone.capstone7.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long parentId;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Comment(String content, Member member, Board board) {
        this.content = content;
        this.member = member;
        this.board = board;
    }

    public Long updateComment(String newContent){
        this.content = newContent;
        return this.id;
    }

    public Long updateParentId(Long parentId){
        this.parentId = parentId;
        return this.parentId;
    }
}
