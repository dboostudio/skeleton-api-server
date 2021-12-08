package studio.dboo.api.module.member;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserMember extends User {
    private Member member;

    public UserMember(Member member){
        super(member.getLoginId(),
                member.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.member = member;
    }
}
