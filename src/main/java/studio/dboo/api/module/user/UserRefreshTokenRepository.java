package studio.dboo.api.module.user;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.dboo.api.module.user.entity.User;
import studio.dboo.api.module.user.entity.UserRefreshToken;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {
    UserRefreshToken findByUserId(String userId);
    UserRefreshToken findByUserIdAndRefreshToken(String userId, String refreshToken);
}
