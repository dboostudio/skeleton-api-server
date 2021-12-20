package studio.dboo.api.infra.auth.token;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.dboo.api.infra.auth.token.RefreshToken;

import javax.transaction.Transactional;

@Transactional
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByLoginId(String loginId);
}
