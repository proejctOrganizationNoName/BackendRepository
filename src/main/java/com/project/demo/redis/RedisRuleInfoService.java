package com.project.demo.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisRuleInfoService {

    private final RedisTemplate<String,String> redisTemplate;

    private final static String projectRuleKey="ruleKey-";


    public void updateRuleData(Long projectId,Long memberId){
        redisTemplate.opsForSet().add(projectRuleKey+projectId,memberId.toString());
    }
    public Boolean checkRuleAgreement(Long projectId,Long memberId){
        return redisTemplate.opsForSet().isMember(projectRuleKey+projectId,memberId.toString());
    }
}
