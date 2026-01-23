package com.project.demo.utility;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponseCreator<T> {

    private T data;
    private String message;

    private ApiResponseCreator(T data, String message) {
        this.data = data;
        this.message = message;

    }
    public static <T> ApiResponseCreator<T> createResponse(T data, String message){
        return new ApiResponseCreator<>(data,message);

    }

}
