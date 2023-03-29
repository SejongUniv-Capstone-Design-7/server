package capstone.capstone7.domain.Member.repository;

import capstone.capstone7.domain.Member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
