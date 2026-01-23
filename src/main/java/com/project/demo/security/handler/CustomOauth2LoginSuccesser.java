package com.project.demo.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.demo.member.domain.Member;
import com.project.demo.redis.RedisUserInfoService;
import com.project.demo.security.domain.CustomOAuth2User;
import com.project.demo.security.service.CustomOauth2Service;
import com.project.demo.utility.jwt.JwtUtility;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

import static com.project.demo.utility.jwt.TokenEnum.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
public class CustomOauth2LoginSuccesser extends SimpleUrlAuthenticationSuccessHandler {

    private  JwtUtility jwtUtility;
    private  RedisUserInfoService redisUserInfoService;
    private  ObjectMapper objectMapper;
    public CustomOauth2LoginSuccesser(JwtUtility jwtUtility, RedisUserInfoService redisUserInfoService, ObjectMapper objectMapper) {
        this.jwtUtility = jwtUtility;
        this.redisUserInfoService = redisUserInfoService;
        this.objectMapper = objectMapper;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try {
            CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            String accessToken = jwtUtility.genAccessToken(customOAuth2User.getId());
            String refreshToken = jwtUtility.genRefreshToken(customOAuth2User.getId());
            String member = objectMapper.writeValueAsString(customOAuth2User.getMember());
            redisUserInfoService.setLoginUserInfo(customOAuth2User.getId(), member, refreshToken);
            response.addHeader(AUTHORIZATION, TOKEN_PREFIX.getValue() + accessToken);
            //로컬에서 테스트시 원하는 주소로 바꾸시면됩니다.
            log.info("{} 유저에대한 로그인이 정상적으로 되었습니다", customOAuth2User.getEmail());
        }
        catch (Exception e){
            log.error("oauth2 로그인 성공중:{}",e.getMessage());
            sendErrorResponse(response,HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
    private void sendErrorResponse(HttpServletResponse response, HttpStatus httpStatus, String message) throws
            IOException {
        response.setStatus(httpStatus.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.format("{\"message\":\"%s\"}", message));
    }
}
