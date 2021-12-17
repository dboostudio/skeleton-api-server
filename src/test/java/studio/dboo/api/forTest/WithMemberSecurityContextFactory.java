package studio.dboo.api.forTest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import studio.dboo.api.module.member.domain.Member;
import studio.dboo.api.module.member.MemberService;

@RequiredArgsConstructor
public class WithMemberSecurityContextFactory implements WithSecurityContextFactory<WithMember> {

    private final MemberService memberService;

    @Override
    public SecurityContext createSecurityContext(WithMember withMember) {

        Member member = Member.builder()
                .loginId(withMember.memberId())
                .password(withMember.password())
                .build();
        memberService.loginAndGenerateTokens(member);

        return SecurityContextHolder.getContext();
    }
}
