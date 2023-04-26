package org.koreait.rest;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("org.koreait.rest")
public class CommonRestController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<JSONResult<Object>> errorHandler(Exception e){
        JSONResult<Object> jsonResult = new JSONResult<>();
        jsonResult.setSuccess(false);
        jsonResult.setMessage(e.getMessage());

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if(e instanceof RestCommonException){
            RestCommonException restE = (RestCommonException) e;
            status = restE.getStatus();
        }
        jsonResult.setStatus(status);
        return ResponseEntity.status(status).body(jsonResult);
    }

}
