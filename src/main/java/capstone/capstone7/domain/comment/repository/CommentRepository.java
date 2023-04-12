package capstone.capstone7.domain.comment.repository;

import capstone.capstone7.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
