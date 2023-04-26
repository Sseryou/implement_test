package org.koreait.rest;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class JSONResult<T> {

    private boolean success;
    private HttpStatus status;
    private T data;
    private String message;

}
