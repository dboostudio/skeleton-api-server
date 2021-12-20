package studio.dboo.api.module.member.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import studio.dboo.api.module.member.entity.Member;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
public class UserLogin {

    @NotEmpty
    @ApiModelProperty(value = "로그인 아이디")
    private String loginId;

    @NotEmpty
    @ApiModelProperty(value = "비밀번호")
    private String password;

    public Member convertToMember(){
        return Member.builder()
                .loginId(this.loginId)
                .password(this.password)
                .build();
    }

}
