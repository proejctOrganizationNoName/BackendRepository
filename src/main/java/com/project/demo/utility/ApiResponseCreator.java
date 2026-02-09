package com.project.demo.utility;


import com.project.demo.rule.domain.RuleAgreementRespones;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.project.demo.rule.domain.RuleAgreementRespones.*;

@Getter
public class ApiResponseCreator<T> {

    private T data;
    private String message;
    private RuleDto ruleDto=null;

    private ApiResponseCreator(T data, String message) {
        this.data = data;
        this.message = message;

    }
    public static <T> ApiResponseCreator<T> createResponse(T data, String message){
        return new ApiResponseCreator<>(data,message);

    }

    public void updateRuleDto(RuleDto ruleDto){
        this.ruleDto=ruleDto;
    }
}
