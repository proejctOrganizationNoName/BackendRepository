package com.project.demo.redis;

import com.project.demo.IntegralTestEnv;
import jakarta.validation.constraints.AssertTrue;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class redisTest extends IntegralTestEnv {




    @Test
    @DisplayName("인증 번호 체크및 삭제 테스트")
    void testing(){
        redisUserInfoService.createAuthCode("test","1234");
        redisUserInfoService.checkAuthCode("test","1234");
        Assertions.assertThat(redisUserInfoService.authCodePassed("test")).isFalse();
    }
}
