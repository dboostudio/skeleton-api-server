package studio.dboo.api.module.member;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;
import studio.dboo.api.module.address.Address;
import studio.dboo.api.module.common.domain.BaseEntity;
import studio.dboo.api.module.member.enums.Gender;
import studio.dboo.api.module.member.enums.Role;
import studio.dboo.api.module.member.enums.Tier;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Member extends BaseEntity {

    final static String EMAIL_REGEXP = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";


    @Id @GeneratedValue
    private Long id;

    /** 인증정보 */
    @Pattern(regexp = EMAIL_REGEXP)
    @ApiModelProperty(value="아이디 (이메일주소)")
    @NotBlank
    private String loginId;
    private String oAuth2Id;

    private Role role;

    @ApiModelProperty(value="비밀번호")
    @NotBlank
//    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$)" , message = "패스워드 형식에 맞지 않습니다.")
//    @Length(min = 8, max = 20)
    private String password;

    /** 개인정보 */
    @ApiModelProperty(value = "휴대전화번호")
    private String phone;

    @OneToOne
    @ApiModelProperty(value="주소")
    private Address address;

    @ApiModelProperty(value = "나이")
    @PositiveOrZero
    @Max(value = 123) // 기네스 등재 최고령의 한국나이 123세
    private Integer age;

    @ApiModelProperty(value="생일")
    private LocalDate birth;

    @ApiModelProperty(value="성별")
    private Gender gender;

    /** 회원정보 */
    @ApiModelProperty(value="별명")
    private String nickname;

    @ApiModelProperty(value = "포인트")
    private Integer point;

    @ApiModelProperty(value = "등급")
    private Tier tier;

    /** Encrypt Password */
    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }



}
