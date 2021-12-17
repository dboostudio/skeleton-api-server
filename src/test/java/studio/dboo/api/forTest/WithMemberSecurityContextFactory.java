package studio.dboo.api.forTest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import studio.dboo.api.module.user.entity.User;
import studio.dboo.api.module.user.UserService;

@RequiredArgsConstructor
public class WithMemberSecurityContextFactory implements WithSecurityContextFactory<WithMember> {

    private final UserService memberService;

    @Override
    public SecurityContext createSecurityContext(WithMember withMember) {

        User member = User.builder()
                .loginId(withMember.memberId())
                .password(withMember.password())
                .build();
        memberService.loginAndGenerateTokens(member);

        return SecurityContextHolder.getContext();
    }
}
