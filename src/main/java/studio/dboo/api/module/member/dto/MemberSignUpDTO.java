package studio.dboo.api.module.member.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;
import studio.dboo.api.module.member.domain.Member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberSignUpDTO {

    private final static String ID_REGEXP = "[a-zA-Z\\d_.]{0,}";
    private final static String ID_BLANK = "아이디를 입력해주세요.";
    private final static String ID_WRONG_LENGTH = "아이디는 50자까지만 입력 가능합니다.";
    private final static String ID_WRONG_PATTERN = "아이디가 형식에 맞지 않습니다.";

    private final static String PASSWORD_REGEXP = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*_=,.])[A-Za-z\\d!@#$%^&*_=,.]{0,}$";
    private final static String PWD_BLANK = "패스워드를 입력해주세요.";
    private final static String PWD_WRONG_LENGTH = "비밀번호는 8자 이상, 16자 이하로 입력해주세요.";
    private final static String PWD_WRONG_PATTERN = "패스워드가 형식에 맞지 않습니다.";


    @NotBlank(message = ID_BLANK)
    @Length(max = 50, message = ID_WRONG_LENGTH)
    @Pattern(regexp = ID_REGEXP, message = ID_WRONG_PATTERN)
    @ApiModelProperty(value = "로그인 아이디")
    private String loginId;

    @NotBlank(message = PWD_BLANK)
    @Length(min = 8, max = 16, message = PWD_WRONG_LENGTH)
    @Pattern(regexp = PASSWORD_REGEXP, message = PWD_WRONG_PATTERN)
    @ApiModelProperty(value = "비밀번호")
    private String password;

    public Member convertToMember(){

        return Member.builder()
                .loginId(this.loginId)
                .password(this.password)
                .build();
    }
}
