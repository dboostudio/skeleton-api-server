package studio.dboo.api.module.v1.member;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import studio.dboo.api.module.v1.member.dto.MemberSignUp;
import studio.dboo.api.module.v1.member.repository.MemberRepository;
import studio.dboo.api.module.v1.member.vo.Member;
import studio.dboo.api.module.v1.member.vo.MemberPrincipal;

import java.util.Optional;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

//        QMember qMember = QMember.member;
//        Predicate predicate = qMember.loginId.eq(loginId);
        Optional<Member> user = memberRepository.findByLoginId(loginId);
//        Optional<Member> user = memberRepository.findOne(predicate);
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
