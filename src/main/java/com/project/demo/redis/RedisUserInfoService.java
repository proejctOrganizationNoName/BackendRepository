package com.project.demo.redis;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class RedisUserInfoService {

    private final RedisTemplate<String,String> redisTemplate;
    private final static String authCodeKey="member-authcode-key-";
    private final static String userInfoKey="member-info-key-";
    private final static String userRefreshTokenKey="member-refresh-key-";
    private final static String userProjectKey="member-project-key-";
    private final StringRedisTemplate stringRedisTemplate;

    public void createAuthCode(String email,String authCode){
        redisTemplate.opsForValue().set(authCodeKey+email,authCode,300, TimeUnit.SECONDS);
    }
    public void checkAuthCode(String email,String authCode){
        Long ans=stringRedisTemplate.execute(new DefaultRedisScript<>(RedisLuaScript.checkAuthKey,Long.class),
                    List.of(authCodeKey+email),authCode);
        if(ans!=1){
            throw new RuntimeException("에러");
        }
    }
    public void setLoginUserInfo(Long id,String member,String refreshToken){
        stringRedisTemplate.execute(new DefaultRedisScript<>(RedisLuaScript.setLoginUserInfo),
                List.of(userInfoKey+id,userRefreshTokenKey+id)
                ,member,refreshToken,String.valueOf(TimeUnit.DAYS.toSeconds(30L)));
    }
    public void logOutUserInfo(Long id){
        stringRedisTemplate.execute(new DefaultRedisScript<>(RedisLuaScript.logOutUserInfo),
                List.of(userInfoKey+id,userRefreshTokenKey+id));
    }
    public void setRedisUserInfo(Long id,String member){
        String key=userInfoKey+id;
        Long ttl = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        if (ttl != null && ttl > 0) {
            redisTemplate.opsForValue().set(key, member, ttl, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(key, member,TimeUnit.DAYS.toSeconds(30L),TimeUnit.SECONDS);
        }
    }
    public void setUserProjectKey(Long memberId,Long projectId){

        redisTemplate.opsForSet().add(userProjectKey+memberId,projectId.toString());
    }
    public void delUserProjectKey(Long memberId,Long projectId){
        redisTemplate.opsForSet().remove(userProjectKey+memberId,projectId.toString());
    }
    public Boolean checkExistProjectKey(Long memberId,Long projectId){
        return redisTemplate.opsForSet().isMember(userProjectKey+memberId,projectId);
    }


    public String getUserInfo(Long id){
        return redisTemplate.opsForValue().get(userInfoKey+id);
    }

    public void saveRefreshToken(Long memberId,String refreshToken){
        redisTemplate.opsForValue().set(userRefreshTokenKey+memberId,refreshToken,30,TimeUnit.DAYS);
    }
    public Boolean existRefreshToken(Long memberId){
        return  redisTemplate.opsForValue().get(userRefreshTokenKey+memberId)!=null;
    }
}
