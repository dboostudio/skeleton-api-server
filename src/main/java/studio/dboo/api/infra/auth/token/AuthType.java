package studio.dboo.api.infra.auth.token;

import lombok.Getter;

@Getter
public enum AuthType {
    GOOGLE,
    FACEBOOK,
    NAVER,
    KAKAO,
    DBOOSTUDIO
}
