package capstone.capstone7.domain.board.repository;

import capstone.capstone7.domain.board.entity.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("select b from Board b join fetch b.member")
    Slice<Board> findBoardListBy(Pageable pageable);


}
