package studio.dboo.api.module.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import studio.dboo.api.infra.encoders.BCryptEncoder;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String PASSWORD_NOT_MATCH = "비밀번호가 일치하지 않습니다.";


    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Optional<Member> byUserId = memberRepository.findByLoginId(memberId);
        Member member = byUserId.orElseThrow(()-> new UsernameNotFoundException(memberId));
        return new User(member.getLoginId(),
                member.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

    public void login(Member member) {
        Optional<Member> byMemberId = memberRepository.findByLoginId(member.getLoginId());
        Member foundMember = byMemberId.orElseThrow(() -> new UsernameNotFoundException(member.getLoginId()));

        if(!passwordEncoder.matches(member.getPassword(), foundMember.getPassword())){
            throw new BadCredentialsException(PASSWORD_NOT_MATCH);
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new UserMember(member),
                member.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    // 현재 세션의 인증된 사용자 정보를 가져온다.
    public Member getCurrentMember() {
        UserMember userMember = (UserMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Member> member = memberRepository.findByLoginId(userMember.getUsername());
        return member.orElseThrow(() -> new UsernameNotFoundException(userMember.getUsername()));
    }

    // 사용자 객체가 현재 세션에 인증된 사용자와 동일한지 판단
    public void isMySelf(Member member) {
        if(member.getLoginId() != getCurrentMember().getLoginId()){
            // throw new Exception
        }
    }

}
