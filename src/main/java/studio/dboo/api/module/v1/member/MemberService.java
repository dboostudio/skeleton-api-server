package studio.dboo.api.module.v1.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import studio.dboo.api.module.v1.member.dto.MemberSignUp;
import studio.dboo.api.module.v1.member.entity.Member;
import studio.dboo.api.module.v1.member.entity.MemberPrincipal;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Optional<Member> user = memberRepository.findByLoginId(loginId);
        return MemberPrincipal.create(user.orElseThrow(()-> new UsernameNotFoundException(loginId)));
    }

    public Member signUp(MemberSignUp signUp) {
        Member newMember = Member.builder()
                .loginId(signUp.getLoginId())
                .password(signUp.getPassword())
                .build();
        newMember.encodePassword(passwordEncoder);
        return memberRepository.save(newMember);
    }
}
