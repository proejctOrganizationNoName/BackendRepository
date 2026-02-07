package com.project.demo.rule.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RuleAgreementRespones {



    @NoArgsConstructor
    @Getter
    public final static class RuleDto{
        private String content;
        private Long ruleId;
        @Builder
        public RuleDto(String content, Long ruleId) {
            this.content = content;
            this.ruleId = ruleId;
        }
    }
}
