package com.utils.controller;

import com.utils.dto.ApiError;
import com.utils.utils.EncoderUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handlerException(Exception e){
        ApiError error = ApiError.builder()
                .code("99")
                .message("Error general")
                .build();
        String response = EncoderUtils.toJson(error);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handlerRuntimeException(RuntimeException e){
        ApiError error = ApiError.builder()
                .code("2")
                .message(e.getMessage())
                .build();
        String response = EncoderUtils.toJson(error);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
