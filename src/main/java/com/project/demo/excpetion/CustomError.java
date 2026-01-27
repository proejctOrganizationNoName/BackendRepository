package com.project.demo.excpetion;

import lombok.Getter;

@Getter
public class CustomError extends RuntimeException{

    public CustomError(String message) {
        super(message);
    }
}
