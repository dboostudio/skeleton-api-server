package studio.dboo.api.module.member.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Gender {
    M("male", 0),
    F("female", 1);

    private String desc;
    private Integer value;
}
