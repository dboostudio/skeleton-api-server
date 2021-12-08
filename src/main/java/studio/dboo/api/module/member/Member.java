package studio.dboo.api.module.member;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import studio.dboo.api.module.address.Address;
import studio.dboo.api.module.member.enums.Gender;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Member {

    final static String EMAIL_REGEXP = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";


    @Id @GeneratedValue
    private Long id;

    /** 인증정보 */
    @ApiModelProperty(value="아이디")
    @NotBlank
    private String loginId;

    @ApiModelProperty(value="비밀번호")
    @NotBlank
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$)" , message = "패스워드 형식에 맞지 않습니다.")
    @Size(min = 8, max = 20)
    private String password;

    /** 개인정보 */
    @ApiModelProperty(value = "휴대전화번호")
    private String phone;

    @OneToOne
    @ApiModelProperty(value="주소")
    private Address address;

    @ApiModelProperty(value = "나이")
    @Positive
    private Integer age;

    @ApiModelProperty(value="생일")
    @Positive
    private LocalDateTime birth;

    @ApiModelProperty(value="이메일")
    @Pattern(regexp = EMAIL_REGEXP)
    private String email;

    @ApiModelProperty(value="성별")
    private Gender gender;

    /** 회원정보 */
    @ApiModelProperty(value="별명")
    private String nickname;

    /** Encrypt Password */
    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }



}
