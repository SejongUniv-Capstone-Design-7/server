package capstone.capstone7.domain.like.repository;

import capstone.capstone7.domain.board.entity.Board;
import capstone.capstone7.domain.like.entity.BoardLike;
import capstone.capstone7.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<BoardLike, Long> {
    Boolean existsByBoardAndMember(Board board, Member member);
    Optional<BoardLike> findByBoardAndMember(Board board, Member member);

    @Query("select l.member.id from BoardLike l where l.board = :board")
    List<Long> findLikeMemberIdsByBoard(@Param("board") Board board);
}
