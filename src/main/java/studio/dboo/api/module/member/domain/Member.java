package studio.dboo.api.module.member.domain;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import studio.dboo.api.module.address.Address;
import studio.dboo.api.module.common.domain.BaseEntity;
import studio.dboo.api.module.member.enums.Gender;
import studio.dboo.api.module.member.enums.Role;
import studio.dboo.api.module.member.enums.Tier;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Member extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    /** AUTH INFO */
    private String loginId;
    private String oAuth2Id;
    private String password;
    private Role role;

    /** PRIVATE INFO */
    private String name;
    private Gender gender;
    private String phone;
    private Integer age;
    private LocalDate birth;
    @OneToOne
    private Address address;

    /** MEMBER INFO */
    private String nickname;
    private Integer point;
    private Tier tier;

    /** RECEIVE-AGREE INFO */
    private Boolean exceptMail;
    private Boolean exceptSMS;
    private Boolean exceptPush;

    /** ENCRYPT PASSWORD */
    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

}
