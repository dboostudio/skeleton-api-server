package studio.dboo.api.module.user;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.dboo.api.module.user.entity.User;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginId(String memberId);

    Boolean existsByLoginId(String loginId);
}
