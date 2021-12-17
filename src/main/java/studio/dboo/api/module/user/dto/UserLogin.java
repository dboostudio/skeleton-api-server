package studio.dboo.api.module.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import studio.dboo.api.module.user.entity.User;

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

    public User convertToMember(){
        return User.builder()
                .loginId(this.loginId)
                .password(this.password)
                .build();
    }

}
