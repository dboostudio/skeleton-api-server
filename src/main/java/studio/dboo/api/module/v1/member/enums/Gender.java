package studio.dboo.api.module.v1.member.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Gender {
    M   ("male", 0),
    F   ("female", 1);

    private final String desc;
    private final Integer value;
}
