package com.project.demo.excpetion;


import com.project.demo.utility.ApiResponseCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionController {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseCreator<String>> standardExceptionHandler(Exception e){
        log.error("error description in error handler:{}",e.getMessage());
        return new ResponseEntity<>(ApiResponseCreator.createResponse(
                null,"에러가 발생했습니다.재시도해주세요"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomError.class)
    public ResponseEntity<ApiResponseCreator<String>> customExceptionHandler(CustomError e){
        log.error("error description in error handler:{}",e.getMessage());
        return new ResponseEntity<>(ApiResponseCreator.createResponse(
                null,"에러가 발생했습니다.재시도해주세요"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
