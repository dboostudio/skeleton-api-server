package studio.dboo.api.module.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import studio.dboo.api.module.member.entity.Member;
import studio.dboo.api.module.member.entity.MemberPrincipal;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Optional<Member> user = userRepository.findByLoginId(loginId);
        return MemberPrincipal.create(user.orElseThrow(()-> new UsernameNotFoundException(loginId)));
    }

}
