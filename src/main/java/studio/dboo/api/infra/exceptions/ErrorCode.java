package studio.dboo.api.infra.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    EXPIRED_TOKEN       ("Token Expired"),
    INVALID_TOKEN       ("Invalid Token"),
    NOTFOUND_TOKEN       ("Jwt claims is empty"),
    UNSUPPORTED_TOKEN   ("Unsupported JWT exception");

    private final String message;
}
