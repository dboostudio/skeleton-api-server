package studio.dboo.api.module.v1.member.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import studio.dboo.api.infra.auth.token.AuthType;
import studio.dboo.api.module.v1.common.domain.Address;
import studio.dboo.api.module.v1.common.domain.TimeAuditingEntity;
import studio.dboo.api.module.v1.member.enums.Gender;
import studio.dboo.api.module.v1.member.enums.RoleType;
import studio.dboo.api.module.v1.member.enums.Tier;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Getter @Builder @AllArgsConstructor @NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class Member extends TimeAuditingEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** AUTH INFO */
    private String loginId;
    @Enumerated(EnumType.STRING)
    private AuthType authType;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleType role;

    /** PRIVATE INFO */
    private String name;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String phone;
    private Integer age;
    private LocalDate birth;
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Address> addressList;

    /** MEMBER INFO */
    private String nickname;
    private String profileImageUrl;
    private Integer point;
    @Enumerated(EnumType.STRING)
    private Tier tier;

    /** VERIFIED INFO */
    private Boolean emailVerified;
    private Boolean mobileVerified;

    /** RECEIVE-AGREE INFO */
    private Boolean exceptMail;
    private Boolean exceptSMS;
    private Boolean exceptPush;

    /** ENCRYPT PASSWORD */
    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

}
