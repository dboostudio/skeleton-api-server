package studio.dboo.api.forTest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import studio.dboo.api.module.member.Member;
import studio.dboo.api.module.member.MemberRepository;
import studio.dboo.api.module.member.MemberService;

import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
public class WithMemberSecurityContextFactory implements WithSecurityContextFactory<WithMember> {

    private final MemberService memberService;

    @Override
    public SecurityContext createSecurityContext(WithMember withMember) {

        Member member = Member.builder()
                .loginId(withMember.memberId())
                .password(withMember.password())
                .build();
        memberService.login(member);

        return SecurityContextHolder.getContext();
    }
}
