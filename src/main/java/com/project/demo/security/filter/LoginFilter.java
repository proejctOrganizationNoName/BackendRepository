package com.project.demo.security.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.demo.member.domain.Member;
import com.project.demo.redis.RedisUserInfoService;
import com.project.demo.security.domain.CustomUserDetail;
import com.project.demo.utility.jwt.JwtUtility;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static com.project.demo.utility.jwt.TokenEnum.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private ObjectMapper objectMapper;

    private JwtUtility jwtUtility;

    private RedisUserInfoService redisUserInfoService;

    public LoginFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper, JwtUtility jwtUtility, RedisUserInfoService redisUserInfoService) {
        this.authenticationManager=authenticationManager;
        setFilterProcessesUrl("/member/request/login");
        this.objectMapper=objectMapper;
        this.jwtUtility=jwtUtility;
        this.redisUserInfoService=redisUserInfoService;
    }

    @Override
    public void setFilterProcessesUrl(String filterProcessesUrl) {
        super.setFilterProcessesUrl(filterProcessesUrl);
    }

    @Override
   public Authentication attemptAuthentication(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws AuthenticationException {
        try(InputStream inputStream=request.getInputStream()) {
            Map<String, String> data = objectMapper.readValue(inputStream, Map.class);
            String mail=data.get("mail");
            String password=data.get("password");
            UsernamePasswordAuthenticationToken token=
                    new UsernamePasswordAuthenticationToken(mail,password);
            return authenticationManager.authenticate(token);
        }
        catch (IOException e){
            log.debug("유저정보 파싱중 발생한 에러");
            throw new RuntimeException("유저 정보 파싱중 발생한 에러");
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        if(failed.getClass().getSimpleName().equals("BadCredentialsException")){
            sendErrorResponse(response,HttpStatus.BAD_REQUEST,"비밀번호가 일치하지 않습니다.");
            return ;
        }
        sendErrorResponse(response,HttpStatus.UNAUTHORIZED,failed.getMessage());
    }

    @Override
    protected void successfulAuthentication(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain chain, Authentication authResult) throws IOException, ServletException {
        try {
            CustomUserDetail customUserDetail = (CustomUserDetail) authResult.getPrincipal();
            Member member = customUserDetail.getMember();
            String accessToken = jwtUtility.genAccessToken(member.getId());
            String refreshToken = jwtUtility.genRefreshToken(member.getId());

            redisUserInfoService.saveRefreshToken(member.getId(), refreshToken);
            settingUserData(member,refreshToken);
            response.setHeader(AUTHORIZATION, TOKEN_PREFIX + accessToken);
            response.setStatus(200);
        }
        catch (Exception e){
            log.info("인증 성공 수행중 에러발생:{}",e.getMessage());
            sendErrorResponse(response,HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
    private void settingUserData(Member member,String refreshToken) throws JsonProcessingException {
            redisUserInfoService.setLoginUserInfo(member.getId()
                    ,objectMapper.writeValueAsString(member),refreshToken);
    }

    private void sendErrorResponse(HttpServletResponse response, HttpStatus httpStatus, String message) throws
            IOException {
        response.setStatus(httpStatus.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.format("{\"message\":\"%s\"}", message));
    }
}
