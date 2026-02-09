package com.project.demo.utility;

import com.project.demo.rule.domain.Rule;
import com.project.demo.rule.repository.AdvanceRuleRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import static com.project.demo.rule.domain.RuleAgreementRespones.*;


@Component
@RequiredArgsConstructor
public class RuleResponseFactory implements ResponseBodyAdvice {
    private final AdvanceRuleRepository advanceRuleRepository;
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }
    @Nullable
    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if(response.getHeaders().containsHeader("needAgree")){
            if (body instanceof ApiResponseCreator) {

                // 헤더 값 추출
                String headerValue = response.getHeaders().getFirst("needAgree");
                if (headerValue != null) {
                    Long ruleId = Long.parseLong(headerValue);
                    Rule rule = advanceRuleRepository.findByProjectId(ruleId);
                    RuleDto ruleDto = RuleDto.builder()
                                .content(rule.getContent())
                                .ruleId(rule.getRuleId())
                                .build();
                    ApiResponseCreator data = (ApiResponseCreator) body;
                    data.updateRuleDto(ruleDto);
                }
            }
            response.getHeaders().remove("needAgree");
        }

        return body;
    }
}
