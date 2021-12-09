package studio.dboo.api.infra.jwt.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtTokenType {

    ACCESS_TOKEN("accessToken"),
    REFRESH_TOKEN("refreshToken");

    private final String type;
}
