package capstone.capstone7.domain.comment.repository;

import capstone.capstone7.domain.board.entity.Board;
import capstone.capstone7.domain.comment.dto.response.ChildCommentGetResponseDto;
import capstone.capstone7.domain.comment.dto.response.ParentCommentGetResponseDto;
import capstone.capstone7.domain.comment.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "select new capstone.capstone7.domain.comment.dto.response.ParentCommentGetResponseDto(c.id, c.content, m.id, m.nickname, c.createdDate) " +
            "from Comment c join c.member m " +
            "where c.board.id = :boardId and c.parentId is null")
    List<ParentCommentGetResponseDto> findAllParentComment(@Param("boardId") Long boardId, Pageable pageable);

    @Query(value = "select new capstone.capstone7.domain.comment.dto.response.ChildCommentGetResponseDto(c.id, c.parentId, c.content, m.id, m.nickname, c.createdDate) " +
            "from Comment c join c.member m " +
            "where c.board.id = :boardId and c.parentId = :parentId")
    List<ChildCommentGetResponseDto> findAllChildComment(@Param("boardId") Long boardId, @Param("parentId") Long parentId, Pageable pageable);

    Integer countByBoard(Board board);

    List<Comment> findAllCommentByBoard(Board board);

    @Query("select exists(select c from Comment c where c.id = :parentId and c.parentId is null)")
    Boolean existsByParentId(@Param("parentId") Long parentId);
}
