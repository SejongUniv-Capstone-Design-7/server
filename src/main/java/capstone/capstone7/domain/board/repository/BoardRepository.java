package capstone.capstone7.domain.board.repository;

import capstone.capstone7.domain.board.entity.Board;
import capstone.capstone7.domain.board.entity.enums.Tag;
import capstone.capstone7.domain.member.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("select b from Board b join fetch b.member where b.tag = :tag")
    Slice<Board> findBoardListBy(@Param("tag") Tag tag, Pageable pageable);

    List<Board> findAllBoardByMember(Member member);
}
