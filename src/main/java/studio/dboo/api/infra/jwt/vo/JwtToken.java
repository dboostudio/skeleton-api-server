package studio.dboo.api.infra.jwt.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JwtToken {

    private String token;
    private JwtTokenType tokenType;

}
