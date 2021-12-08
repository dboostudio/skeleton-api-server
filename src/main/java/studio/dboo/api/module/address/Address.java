package studio.dboo.api.module.address;

import lombok.*;
import studio.dboo.api.module.member.Member;

import javax.persistence.*;

@Entity
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Address {
    @Id @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "address")
    private Member member;
}
