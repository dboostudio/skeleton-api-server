package studio.dboo.api.module.common.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class ResponseVO<T> {

    private HttpStatus httpStatus;
    private String message;
    private T data;

}
