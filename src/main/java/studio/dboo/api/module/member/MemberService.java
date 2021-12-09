package studio.dboo.api.module.member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import studio.dboo.api.infra.exceptions.CustomException;
import studio.dboo.api.infra.jwt.JwtTokenUtil;
import studio.dboo.api.infra.jwt.vo.JwtToken;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenUtil jwtTokenUtil;

    private static final String PASSWORD_NOT_MATCH = "비밀번호가 일치하지 않습니다.";


    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Optional<Member> byUserId = memberRepository.findByLoginId(memberId);
        Member member = byUserId.orElseThrow(()-> new UsernameNotFoundException(memberId));
        return new User(member.getLoginId(),
                member.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

    public HashMap<Integer, JwtToken> loginAndGenerateTokens(Member member) {
        // 가입여부, 패스워드 체크
        Optional<Member> byUsername = memberRepository.findByLoginId(member.getLoginId());
        Member savedAccount = byUsername.orElseThrow(()-> new UsernameNotFoundException(member.getLoginId()));

        if(!passwordEncoder.matches(member.getPassword(),savedAccount.getPassword())){
            throw new BadCredentialsException(PASSWORD_NOT_MATCH);
        }

        // 아이디 패스워드 일치 시, authentication등록
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(member.getLoginId(), member.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 등록한 authentication을 기반으로 token발급
        return jwtTokenUtil.generateJwtTokens(authentication);
    }

    public Member signUp(Member member) {

        // 이미 가입된 회원아이디인지 체크
        if(memberRepository.existsByLoginId(member.getLoginId())){
            throw new CustomException(HttpStatus.BAD_REQUEST, "");
        }

        // 가입처리
        member.encodePassword(passwordEncoder);
        Member savedMember = memberRepository.save(member);

        return savedMember;
    }
}
