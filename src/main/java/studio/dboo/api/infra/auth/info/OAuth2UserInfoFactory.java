package studio.dboo.api.infra.auth.info;

import studio.dboo.api.infra.auth.info.impl.FacebookOAuth2UserInfo;
import studio.dboo.api.infra.auth.info.impl.GoogleOAuth2UserInfo;
import studio.dboo.api.infra.auth.info.impl.KakaoOAuth2UserInfo;
import studio.dboo.api.infra.auth.info.impl.NaverOAuth2UserInfo;
import studio.dboo.api.infra.auth.token.AuthType;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(AuthType authType, Map<String, Object> attributes) {
        switch (authType) {
            case GOOGLE: return new GoogleOAuth2UserInfo(attributes);
            case FACEBOOK: return new FacebookOAuth2UserInfo(attributes);
            case NAVER: return new NaverOAuth2UserInfo(attributes);
            case KAKAO: return new KakaoOAuth2UserInfo(attributes);
            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}
