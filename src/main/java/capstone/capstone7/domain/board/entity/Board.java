package capstone.capstone7.domain.board.entity;

import capstone.capstone7.domain.member.entity.Member;
import capstone.capstone7.domain.board.dto.request.BoardUpdateRequestDto;
import capstone.capstone7.domain.board.entity.enums.Tag;
import capstone.capstone7.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import static java.lang.Boolean.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private Tag tag;
    private String image;
    private Boolean isSolved;

    @ColumnDefault("0")
    private Integer likeNum;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Board(String title, String content, Tag tag, String image, Member member) {
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.image = image;
        this.likeNum = 0;
        this.isSolved = FALSE;
        this.member = member;
    }

    public void boardContentUpdate(String boardImage, BoardUpdateRequestDto boardUpdateRequestDto){
        if(boardImage != null){
            this.image = boardImage;
        }else{
            this.image = null;
        }

        if(boardUpdateRequestDto.getTitle() != ""){
            this.content = boardUpdateRequestDto.getTitle();
        }

        if(boardUpdateRequestDto.getContent() != ""){
            this.title = boardUpdateRequestDto.getContent();
        }

        if(boardUpdateRequestDto.getTag() != null){
            this.tag = boardUpdateRequestDto.getTag();
        }
    }

    public Integer addLike(){
        this.likeNum += 1;
        return this.likeNum;
    }

    public Integer minusLike(){
        this.likeNum -= 1;
        return this.likeNum;
    }
}
