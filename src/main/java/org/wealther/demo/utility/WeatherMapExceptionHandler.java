package org.wealther.demo.utility;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class WeatherMapExceptionHandler  {

    @ExceptionHandler(value = {WeatherMapBaseError.class})
    public ResponseEntity<Object> handleApiRequestException(WeatherMapBaseError ex) {
        return buildResponseEntity(ex);
    }
    private ResponseEntity<Object> buildResponseEntity(WeatherMapBaseError weatherMapBaseError) {
        ResponseEntity<Object> responseEntity = ResponseEntity.status(weatherMapBaseError.getStatus())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(WeatherMapError.builder().errorCode(weatherMapBaseError.getStutsCode()).errorMessage(weatherMapBaseError.getMessage()).build());
        return responseEntity;
    }

}
