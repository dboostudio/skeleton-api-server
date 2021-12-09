package studio.dboo.api.module.member.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Tier {
    BRONZE      (0),
    SILVER      (1),
    GOLD        (2),
    PLATINUM    (3),
    DIAMOND     (4),
    KING        (5);

    private final Integer value;
}
