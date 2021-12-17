package studio.dboo.api.module.oauth.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

public interface SocialOAuthService {
    /**
     * 각 social login 페이지로 redirect 처리할 URL build
     * 사용자로부터 로그인 요청을 받아 social login server 인증용 code 요청
     */

    String getOauthRedirectURL();

    Map<String,Object> requestAccessToken(String code) throws JsonProcessingException;
}
