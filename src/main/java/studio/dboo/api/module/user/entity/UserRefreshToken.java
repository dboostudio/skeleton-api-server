package studio.dboo.api.module.user.entity;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter @Builder @AllArgsConstructor @NoArgsConstructor
public class UserRefreshToken {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    @Size(max = 256)
    private String token;
}
