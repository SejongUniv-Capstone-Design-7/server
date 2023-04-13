package capstone.capstone7.domain.comment.repository;

import capstone.capstone7.domain.comment.dto.response.CommentGetResponseDto;
import capstone.capstone7.domain.comment.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "select new capstone.capstone7.domain.comment.dto.response.CommentGetResponseDto(c.id, c.content, m.id, m.nickname, c.modifiedDate) " +
            "from Comment c join c.member m " +
            "where c.board.id = :boardId " +
            "order by c.modifiedDate desc")
    Slice<CommentGetResponseDto> findAllComment(@Param("boardId") Long boardId, Pageable pageable);
}