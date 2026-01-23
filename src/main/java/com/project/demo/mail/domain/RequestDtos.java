package com.project.demo.mail.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

public record RequestDtos() {

    @Getter
    @NoArgsConstructor
    public class RequestAuthCodeDto{
        @NotNull(message = "인증 코드 요구시 이메일은 필수입니다")
        private String email;
        @NotNull(message = "메일 서비스 이용시 메일 타입은 필수입니다")
        private EmailType emailType;
    }

    @Getter
    @NoArgsConstructor
    public class RequestCheckAuthCodeDto{
        @NotNull(message = "인증 요구시 이메일은 필수입니다")
        private String email;
        @NotNull(message = "인증 요구시 코드는 필수입니다")
        private String authCode;
    }
}
