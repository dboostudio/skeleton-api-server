package studio.dboo.api.module.v1.member.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import studio.dboo.api.module.v1.member.vo.Member;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberLogin {

    @NotBlank
    @ApiModelProperty(value = "로그인 아이디")
    private String loginId;

    @NotBlank
    @ApiModelProperty(value = "비밀번호")
    private String password;

    public Member convertToMember(){
        return Member.builder()
                .loginId(this.loginId)
                .password(this.password)
                .build();
    }

}