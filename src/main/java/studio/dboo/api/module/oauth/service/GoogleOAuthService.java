package studio.dboo.api.module.oauth.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

//@Component
public class GoogleOAuthService implements SocialOAuthService {

    @Value("${spring.security.oauth2.client.registration.google.url}")
    private String GOOGLE_URL;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String GOOGLE_CLI_ID;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String GOOGLE_CLI_SECRET;

    @Value("${spring.security.oauth2.client.registration.google.callback.url}")
    private String GOOGLE_CLI_CALLBACK;

    @Value("${spring.security.oauth2.client.registration.google.token_url}")
    private String GOOGLE_CLI_TOKEN_URL;


    @Override
    public String getOauthRedirectURL() {
        Map<String,Object> map = new HashMap<>();
        map.put("scope","profile%20email");
        map.put("response_type","code");
        map.put("client_id",GOOGLE_CLI_ID);
        map.put("redirect_uri",GOOGLE_CLI_CALLBACK);

        String paramString = map.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return GOOGLE_URL + "?" + paramString;
    }

    @Override
    public Map<String,Object> requestAccessToken(String code) throws JsonProcessingException {
//        RestTemplate restTemplate = new RestTemplate();
//
//        Map<String,Object> map = new HashMap<>();
//        map.put("code",code);
//        map.put("client_id",GOOGLE_CLI_ID);
//        map.put("client_secret",GOOGLE_CLI_SECRET);
//        map.put("redirect_uri",GOOGLE_CLI_CALLBACK);
//        map.put("grant_type","authorization_code");
//
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(GOOGLE_CLI_TOKEN_URL,map,String.class);
//
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//
////        TokenDTO.Google result = mapper.readValue(responseEntity.getBody(), new TypeReference<TokenDTO.Google>() {
////        });
//
//        String requestUrl = UriComponentsBuilder.fromHttpUrl("https://oauth2.googleapis.com/tokeninfo").queryParam("id_token",result.getId_token()).encode().toUriString();
//
//        String resultJson = restTemplate.getForObject(requestUrl,String.class);
//
//        Map<String,String> userInfo = mapper.readValue(resultJson, new TypeReference<Map<String, String>>() {
//        });

        Map<String,Object> resultMap = new HashMap<>();

        // 토큰 파싱 후 사용자 정보 map에 저장.
//        resultMap.put("email",userInfo.get("email"));
//        resultMap.put("name",userInfo.get("name"));

        // 해당 메일이 현재 db에 저장되어 있는지 확인.
        // 해당 메일이 존재할 경우. -> 로그인 처리 ( statusCode = 200 ).
        // 해당 메일이 존재하지 않을 경우. -> 회원가입 ( 신규 등록, statusCode = 204 ).




        return resultMap;
//        if(responseEntity.getStatusCode() == HttpStatus.OK){
//            return responseEntity.getBody();
//        }
//        return "request is failed";
    }
}
