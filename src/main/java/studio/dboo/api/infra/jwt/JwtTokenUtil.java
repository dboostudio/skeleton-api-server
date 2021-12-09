package studio.dboo.api.infra.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import studio.dboo.api.infra.jwt.vo.JwtToken;
import studio.dboo.api.infra.jwt.vo.JwtTokenType;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil implements InitializingBean {


    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;
    private static final String AUTHORITIES_KEY = "roles";

    private static final Long HOUR = 1000L * 60 * 60;
    private static final Long ACCESS_TOKEN_VALID_TIME = HOUR;
    private static final Long REFRESH_TOKEN_VALID_TIME = HOUR * 24;

    public HashMap<Integer, JwtToken> generateJwtTokens(Authentication authentication){
        JwtToken accessToken = JwtToken.builder()
                .token(generateJwtToken(authentication, ACCESS_TOKEN_VALID_TIME))
                .tokenType(JwtTokenType.ACCESS_TOKEN)
                .build();
        JwtToken refreshToken = JwtToken.builder()
                .token(generateJwtToken(authentication, REFRESH_TOKEN_VALID_TIME))
                .tokenType(JwtTokenType.REFRESH_TOKEN)
                .build();

        HashMap<Integer, JwtToken> jwtTokenHashMap = new HashMap();
        jwtTokenHashMap.put(0, accessToken);
        jwtTokenHashMap.put(1, refreshToken);
        return jwtTokenHashMap;
    }

    public String generateJwtToken(Authentication authentication, Long expirationTime){
        String token = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authentication.getAuthorities())
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date().getTime()) + expirationTime ))
                .compact();
        return token;
    }

    public void validateJwtToken(String token){
        Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
    }

    public Authentication getAuthentication(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token).getBody();
        System.out.println(claims.getSubject());
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User user = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(user, token, authorities);
    }

    @Override
    public void afterPropertiesSet() {}
}
