package studio.dboo.api.module.v1.member;


import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import studio.dboo.api.infra.auth.token.AuthToken;
import studio.dboo.api.infra.auth.token.AuthTokenProvider;
import studio.dboo.api.infra.auth.token.RefreshToken;
import studio.dboo.api.infra.auth.token.RefreshTokenRepository;
import studio.dboo.api.infra.properties.AppProperties;
import studio.dboo.api.infra.utils.CookieUtil;
import studio.dboo.api.infra.utils.HeaderUtil;
import studio.dboo.api.module.v1.member.dto.MemberLogin;
import studio.dboo.api.module.v1.member.dto.MemberSignUp;
import studio.dboo.api.module.v1.member.vo.Member;
import studio.dboo.api.module.v1.member.vo.MemberPrincipal;
import studio.dboo.api.module.v1.member.enums.RoleType;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
@Api(value = "회원 API 컨트롤러")
public class MemberController {

    private final MemberService memberService;

    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;

    private final static long THREE_DAYS_MSEC = 259200000;
    private final static String REFRESH_TOKEN = "refresh_token";

    @PostMapping("/sign-up")
    @ApiOperation(value = "signUpMember", notes = "회원가입")
    public ResponseEntity<Member> signUp(@Valid @RequestBody MemberSignUp signUp) {
        Member member = memberService.signUp(signUp);
        return ResponseEntity.ok().body(member);
    }


    @PostMapping("/login")
    public ResponseEntity login(HttpServletRequest request, HttpServletResponse response, @RequestBody MemberLogin memberLogin) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(memberLogin.getLoginId(), memberLogin.getPassword()));

        String loginId = memberLogin.getLoginId();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Date now = new Date();
        AuthToken accessToken = tokenProvider.createAuthToken(
                        loginId, ((MemberPrincipal) authentication.getPrincipal()).getRoleType().getCode()
                        , new Date(now.getTime() + appProperties.getAuth().getTokenExpiry()));

        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
        AuthToken refreshToken = tokenProvider.createAuthToken(appProperties.getAuth().getTokenSecret(), new Date(now.getTime() + refreshTokenExpiry));

        // userId refresh token 으로 DB 확인
        RefreshToken userRefreshToken = refreshTokenRepository.findByLoginId(loginId);
        if (userRefreshToken == null) {
            // 없는 경우 새로 등록
            userRefreshToken = new RefreshToken(loginId, refreshToken.getToken());
            refreshTokenRepository.saveAndFlush(userRefreshToken);
        } else {
            // DB에 refresh 토큰 업데이트
            userRefreshToken.setToken(refreshToken.getToken());
        }

        int cookieMaxAge = (int) refreshTokenExpiry / 60;
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        return ResponseEntity.ok().header("token", accessToken.getToken()).build();
    }

    @GetMapping("/refresh")
    public ResponseEntity refreshToken(HttpServletRequest request, HttpServletResponse response) {
        // access token 확인
        String accessToken = HeaderUtil.getTokenFromRequest(request);
        AuthToken authToken = tokenProvider.convertAuthToken(accessToken);
        if (!authToken.validate()) {
            throw new BadCredentialsException("유효한 토큰이 아닙니다.");
        }

        // expired access token 인지 확인
        Claims claims = authToken.getExpiredTokenClaims();
//        if (claims == null) {
//            throw new OAuth2AuthenticationException();
//        }

        String userId = claims.getSubject();
        RoleType roleType = RoleType.of(claims.get("role", String.class));

        // refresh token
        String token = CookieUtil.getCookie(request, REFRESH_TOKEN).map(Cookie::getValue).orElse((null));
        AuthToken authRefreshToken = tokenProvider.convertAuthToken(token);

        if (authRefreshToken.validate()) {
//            return ApiResponse.invalidRefreshToken();
        }

        // userId refresh token 으로 DB 확인
        RefreshToken userRefreshToken = refreshTokenRepository.findByLoginIdAndToken(userId, token);
        if (userRefreshToken == null) {
//            return ApiResponse.invalidRefreshToken();
        }

        Date now = new Date();
        AuthToken newAccessToken = tokenProvider.createAuthToken(userId, roleType.getCode(), new Date(now.getTime() + appProperties.getAuth().getTokenExpiry()));

        long validTime = authRefreshToken.getTokenClaims().getExpiration().getTime() - now.getTime();

        // refresh 토큰 기간이 3일 이하로 남은 경우, refresh 토큰 갱신
        if (validTime <= THREE_DAYS_MSEC) {
            // refresh 토큰 설정
            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

            authRefreshToken = tokenProvider.createAuthToken(appProperties.getAuth().getTokenSecret(), new Date(now.getTime() + refreshTokenExpiry));

            // DB에 refresh 토큰 업데이트
            userRefreshToken.setToken(authRefreshToken.getToken());

            int cookieMaxAge = (int) refreshTokenExpiry / 60;
            CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
            CookieUtil.addCookie(response, REFRESH_TOKEN, authRefreshToken.getToken(), cookieMaxAge);
        }

//        return ApiResponse.success("token", newAccessToken.getToken());
        return ResponseEntity.ok().build();
    }

}
