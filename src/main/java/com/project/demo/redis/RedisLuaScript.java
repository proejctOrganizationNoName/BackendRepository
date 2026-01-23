package com.project.demo.redis;

public class RedisLuaScript {


    public final static String checkAuthKey="local authCode=redis.call(\"get\",KEYS[1])\n" +
            "if certCode==nil or authCode~=ARGV[1] then\n" +
            "    return 0 \n" +
            "else\n" +
            "    redis.call(\"del\",KEYS[1])\n" +
            "    return 1 \n" +
            "end";


    // 차례대로 회원 정보 저장, 회원 refresh 토큰 저장
    public final static String setLoginUserInfo="redis.call(\"set\", KEYS[1], ARGV[1], \"EX\",ARGV[3])\n" +
            "redis.call(\"set\", KEYS[2], ARGV[2], \"EX\", ARGV[3])\n" +
            "\n";


    public final static String logOutUserInfo="redis.call(\"del\", KEYS[1],KEYS[2])\n";

}
