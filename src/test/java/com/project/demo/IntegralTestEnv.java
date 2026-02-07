package com.project.demo;


import com.project.demo.mail.service.MailService;
import com.project.demo.member.Service.MemberService;
import com.project.demo.member.Service.SecurityMemberReadService;
import com.project.demo.member.domain.Member;
import com.project.demo.member.domain.MemberType;
import com.project.demo.member.repository.MemberRepository;
import com.project.demo.member.repository.MemberRepositoryAdvance;
import com.project.demo.project.domain.Project;
import com.project.demo.project.repository.AdvanceProjectRepo;
import com.project.demo.project.repository.ProjectRepository;
import com.project.demo.project.service.ProjectService;
import com.project.demo.redis.RedisUserInfoService;
import com.project.demo.utility.CustomDateTimeFormat;
import com.project.demo.utility.jwt.JwtUtility;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@ActiveProfiles(value = "test")
@SpringBootTest
public class IntegralTestEnv {

    @Autowired
    protected MemberRepositoryAdvance memberRepositoryImpl;
    @Autowired
    protected AdvanceProjectRepo advanceProjectRepo;
    @Autowired
    protected ProjectRepository projectRepository;

    @Autowired
    protected ProjectService projectService;
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
        projectRepository.deleteAllInBatch();
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

    public Project createProject(){
        Project p= Project.builder()
                .projectName("test")
                .deadLine(LocalDateTime.now())
                .inviteCode(UUID.randomUUID().toString())
                .build();
        p=advanceProjectRepo.createProject(p);
        return p;
    }
}
