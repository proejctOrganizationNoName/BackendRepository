package com.project.demo;


import com.project.demo.mail.service.MailService;
import com.project.demo.member.Service.MemberService;
import com.project.demo.member.Service.SecurityMemberReadService;
import com.project.demo.member.domain.Member;
import com.project.demo.member.domain.MemberType;
import com.project.demo.member.repository.MemberRepository;
import com.project.demo.member.repository.MemberRepositoryAdvance;
import com.project.demo.redis.RedisUserInfoService;
import com.project.demo.utility.jwt.JwtUtility;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(value = "test")
@SpringBootTest
public class IntegralTestEnv {


    @Autowired
    protected MemberRepositoryAdvance memberRepositoryImpl;


    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected MemberService memberService;


    @Autowired
    protected MailService mailService;

    @Autowired
    protected SecurityMemberReadService securityMemberReadService;

    @Autowired
    protected RedisUserInfoService redisUserInfoService;


    @Autowired
    protected JwtUtility jwtUtility;

    @Autowired
    protected MemberRepository memberRepository;


    @AfterEach
    void cleanAfterTest(){
        memberRepository.deleteAllInBatch();
    }

    public Member createMember(Long idx){
        Member m=Member.builder()
                .email("test"+idx+"@test.com")
                .imgUrl(null)
                .memberType(MemberType.LOCAL)
                .nickName("test"+idx)
                .password("test")
                .build();

        m=memberRepositoryImpl.saveMember(m);

        return m;
    }
}
