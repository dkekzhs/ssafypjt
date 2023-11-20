package com.ssafy.web.common;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.message.AuthException;

import org.springframework.http.HttpStatus;
import com.ssafy.web.common.dto.MessageResponseDto;


@RestControllerAdvice
public class GlobalHandlerController {


    @ExceptionHandler(AuthException.class)
    @ResponseBody
    public ResponseEntity<?> handleNotLoggedInException(AuthException ex) {
        return ResponseEntity.ok(MessageResponseDto.builder().message(ex.getMessage()).build());
    }
}
