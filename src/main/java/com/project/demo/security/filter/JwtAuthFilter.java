package com.project.demo.security.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.demo.member.domain.Member;
import com.project.demo.member.repository.MemberRepositoryAdvance;
import com.project.demo.redis.RedisUserInfoService;
import com.project.demo.security.domain.CustomUserDetail;
import com.project.demo.utility.jwt.JwtUtility;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static com.project.demo.utility.jwt.TokenEnum.TOKEN_PREFIX;

public class JwtAuthFilter extends OncePerRequestFilter {


    public JwtAuthFilter(RedisUserInfoService redisUserInfoService, JwtUtility jwtUtility, MemberRepositoryAdvance memberRepository, ObjectMapper objectMapper) {
        this.redisUserInfoService = redisUserInfoService;
        this.jwtUtility = jwtUtility;
        this.memberRepository=memberRepository;
        this.objectMapper=objectMapper;
    }

    private RedisUserInfoService redisUserInfoService;
    private JwtUtility jwtUtility;
    private MemberRepositoryAdvance memberRepository;

    private ObjectMapper objectMapper;


    private static final String[] freePassPath = {};
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return Arrays.stream(freePassPath).anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token=request.getHeader("Authorization");
        if(token==null||!jwtUtility.validToken(token) ) {
            sendErrorResponse(response, HttpStatus.BAD_REQUEST, "토큰이 없거나 유효하지 않는 토큰입니다");
            return;
        }
        Claims claims=jwtUtility.getClaims(token);

        Long memberId=claims.get("id",Long.class);
        if(jwtUtility.isExpire(token)){
            if(redisUserInfoService.existRefreshToken(memberId)){
                response.sendRedirect("redirect");
                return ;
            }
            String newAccessToken=jwtUtility.genAccessToken(memberId);
            response.setHeader("Authorization",TOKEN_PREFIX+newAccessToken);
        }
        Optional<Member> member=getMemberInfo(memberId);

        if(member.isEmpty()){
            sendErrorResponse(response,HttpStatus.BAD_REQUEST,"없는 회원입니다");
            return;
        }

        CustomUserDetail customUserDetail=new CustomUserDetail(member.get());

        Authentication auth=new UsernamePasswordAuthenticationToken(
                customUserDetail,null,customUserDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request,response);

    }


    private Optional<Member> getMemberInfo(Long memberId){
        try {
            String memberInfo = redisUserInfoService.getUserInfo(memberId);
            if (memberInfo == null) {
                Optional<Member> data = memberRepository.findById(memberId);
                if (data.isEmpty() || data.get().getDeleted()) {
                    Optional.empty();
                }
                redisUserInfoService.setRedisUserInfo(memberId, objectMapper.writeValueAsString(data.get()));
                return data;
            } else {
                return Optional.of(objectMapper.readValue(memberInfo, Member.class));
            }
        }
        catch (JsonProcessingException e){
            return Optional.empty();
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
