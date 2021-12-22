package studio.dboo.api.module.v1.address;

import lombok.*;
import studio.dboo.api.module.v1.member.entity.Member;

import javax.persistence.*;

@Entity
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Address {
    @Id @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "address")
    private Member member;

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}
