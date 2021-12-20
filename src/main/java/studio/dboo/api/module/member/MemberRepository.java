package studio.dboo.api.module.member;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.dboo.api.module.member.entity.Member;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface MemberRepository extends JpaRepository<Member, Long> {

    Boolean existsByLoginId(String loginId);

    Optional<Member> findByLoginId(String loginId);
}
