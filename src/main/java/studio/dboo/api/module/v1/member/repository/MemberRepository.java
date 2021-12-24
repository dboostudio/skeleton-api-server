package studio.dboo.api.module.v1.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import studio.dboo.api.module.v1.member.vo.Member;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor {

    Boolean existsByLoginId(String loginId);

    Optional<Member> findByLoginId(String loginId);
}
