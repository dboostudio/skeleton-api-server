package studio.dboo.api.infra.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {
    // 핸들러가 설정되지 않은 오류일 시, INTERNAL_SERVER_ERROR 리턴
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity globalExceptionHandler(Exception e){
        JsonArray result = new JsonArray();
        JsonObject jsonObject = new JsonObject();

        log.error("========== EXCEPTION LOG START ==========");
        log.error("Error SimpleName : {} ", e.getClass().getSimpleName());
        log.error("Error Message : {}", e.getMessage());
        log.error("Error StackTrace : {} ", e);
        log.error("========== EXCEPTION LOG END ============");

        jsonObject.addProperty("field", e.getClass().getSimpleName());
        jsonObject.addProperty("message", e.getMessage());
        result.add(jsonObject);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.toString());
    }

    // bindException시, 핸들링
    @ExceptionHandler(value = BindException.class)
    public ResponseEntity bindExceptionHandler(BindException e) throws JsonProcessingException {
        JsonArray result = new JsonArray();
        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getAllErrors().forEach( error -> {
            JsonObject jsonObject = new JsonObject();
            FieldError field = (FieldError) error;
            log.error("========== ERROR LOG START ==========");
            log.error("BindException occured");
            log.error("Field : {}", field.getField());
            log.error("ObjectName : {}", field.getObjectName());
            log.error("DefaultMessage : {}", field.getDefaultMessage());
            log.error("RejectedValue : {}", field.getRejectedValue());

            jsonObject.addProperty("field", field.getField());
            jsonObject.addProperty("message", field.getDefaultMessage());
            result.add(jsonObject);
            log.error("========== ERROR LOG END ============");
        });
        return ResponseEntity.badRequest().body(result.toString());
    }

    // Validation 실패 시, BAD_REQUEST 리턴
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity methodArgNotValidExceptionHandler(MethodArgumentNotValidException e) throws JsonProcessingException {
        JsonArray result = new JsonArray();
        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getAllErrors().forEach( error -> {
            JsonObject jsonObject = new JsonObject();
            FieldError field = (FieldError) error;
            log.error("========== ERROR LOG START ==========");
            log.error("MethodArgumentNotValidException occured");
            log.error("Field : {}", field.getField());
            log.error("ObjectName : {}", field.getObjectName());
            log.error("DefaultMessage : {}", field.getDefaultMessage());
            log.error("RejectedValue : {}", field.getRejectedValue());

            jsonObject.addProperty("field", field.getField());
            jsonObject.addProperty("message", field.getDefaultMessage());
            result.add(jsonObject);
            log.error("========== ERROR LOG END ============");
        });
        return ResponseEntity.badRequest().body(result.toString());
    }

}
