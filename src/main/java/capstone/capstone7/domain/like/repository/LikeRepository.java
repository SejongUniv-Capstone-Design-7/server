package capstone.capstone7.domain.like.repository;

import capstone.capstone7.domain.Member.entity.Member;
import capstone.capstone7.domain.board.entity.Board;
import capstone.capstone7.domain.like.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<BoardLike, Long> {
    Boolean existsByBoardAndMember(Board board, Member member);
    Optional<BoardLike> findByBoardAndMember(Board board, Member member);
}
