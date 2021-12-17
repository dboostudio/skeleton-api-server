package studio.dboo.api.infra.auth;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import studio.dboo.api.infra.exceptions.ErrorCode;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    /** Bean Injection */
    private final JwtTokenUtil jwtTokenUtil;

    /** Constant */
    public static final String ACCESS_HEADER = "AccessToken";
    public static final String REFRESH_HEADER = "RefreshToken";
    public static final String BEARER_PREFIX = "Bearer ";

    private List<String> EXCLUDE_URL = List.of("/"
            ,"/swagger-ui/**", "/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/v2/api-docs"
            ,"/api/member/sign-up", "/api/member/login");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //TODO - log level debug로 바꿀것
        log.info("JwtFilter > doFilterInternal > request.getRequestURI() > " + request.getRequestURI());
        String jwt = extractTokenFromHeader(request);

        jwtTokenUtil.validateJwtToken(jwt);
        try{
            if(StringUtils.hasText(jwt)){
                Authentication authentication = jwtTokenUtil.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
            request.setAttribute("exception", ErrorCode.INVALID_TOKEN);

        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
            request.setAttribute("exception", ErrorCode.EXPIRED_TOKEN);

        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT exception");
            request.setAttribute("exception", ErrorCode.UNSUPPORTED_TOKEN);

        } catch (IllegalArgumentException ex) {
            System.out.println("Jwt claims string is empty");
            request.setAttribute("exception", ErrorCode.NOTFOUND_TOKEN);
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        String requestContents = request.getHeader(ACCESS_HEADER);
        if(StringUtils.hasText(ACCESS_HEADER) && requestContents.startsWith(BEARER_PREFIX)){
            return requestContents.substring(7);
        }
        return requestContents;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for(String urlPattern : EXCLUDE_URL){
            if(antPathMatcher.match(urlPattern, request.getRequestURI())){
                return true;
            }
        }
        return false;
    }
}
