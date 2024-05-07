package org.wealther.demo.utility;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper=false)
public class WeatherMapBaseError extends RuntimeException {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private String stutsCode;
    public WeatherMapBaseError() {
        timestamp = LocalDateTime.now();
    }

    public WeatherMapBaseError(HttpStatus status) {
        this();
        this.status = status;
    }

    public WeatherMapBaseError(HttpStatus status, String message) {
        this();
        this.status = status;
        this.message = message;
    }
    public WeatherMapBaseError(HttpStatus status,String stutsCode, String message) {
        this();
        this.status = status;
        this.stutsCode = stutsCode;
        this.message = message;
    }
    public WeatherMapBaseError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
//        this.debugMessage = ex.getLocalizedMessage();
        //this.debugMessage = ex.getMessage();
    }
}
