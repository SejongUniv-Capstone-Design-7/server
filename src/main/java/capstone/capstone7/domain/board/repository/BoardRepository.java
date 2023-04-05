package capstone.capstone7.domain.board.repository;

import capstone.capstone7.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
