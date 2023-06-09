package capstone.capstone7.domain.board.repository;

import capstone.capstone7.domain.board.entity.Board;
import capstone.capstone7.domain.board.entity.enums.Tag;
import capstone.capstone7.domain.member.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("select b from Board b join fetch b.member where b.tag = :tag")
    Slice<Board> findBoardListByTag(@Param("tag") Tag tag, Pageable pageable);

    List<Board> findAllBoardByMember(Member member);

    // 일주일 사이 좋아요 가장 많이 눌린 게시물 3개 조회
    // order by 좋아요 개수 많은 순, 최근 날짜 게시물 순
    @Query("select b from Board b join fetch b.member where b.createdDate >= :oneWeekAgo and b.createdDate <= :today order by b.likeNum desc, b.createdDate desc limit 3")
    List<Board> findTop3ByLikeNumDesc(@Param("oneWeekAgo") LocalDateTime oneWeekAgo, @Param("today") LocalDateTime today);
}
