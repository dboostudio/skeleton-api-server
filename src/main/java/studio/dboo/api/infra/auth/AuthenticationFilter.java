package studio.dboo.api.infra.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import studio.dboo.api.infra.auth.token.AuthToken;
import studio.dboo.api.infra.auth.token.AuthTokenProvider;
import studio.dboo.api.infra.utils.HeaderUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final AuthTokenProvider tokenProvider;

    private List<String> EXCLUDE_URL = List.of("/"
            ,"/swagger-ui/**"
            ,"/api/member/sign-up", "/api/member/login");

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)  throws ServletException, IOException {

        String tokenStr = HeaderUtil.parseTokenFromHeader(request);
        AuthToken token = tokenProvider.convertAuthToken(tokenStr);

        if (token.validate()) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for(String urlPattern : EXCLUDE_URL){
            if(antPathMatcher.match(urlPattern, request.getRequestURI())){
                return true;
            }
        }
        return false;
    }
}
