package studio.dboo.api.infra.auth.token;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter @Builder @AllArgsConstructor @NoArgsConstructor
public class RefreshToken {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;

    @Size(max = 256)
    @Setter
    private String token;

    public RefreshToken(String loginId, String token) {
        this.loginId = loginId;
        this.token = token;
    }
}
