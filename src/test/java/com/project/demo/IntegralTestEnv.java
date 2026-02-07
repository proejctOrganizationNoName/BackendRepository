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
import com.project.demo.rule.domain.Agreement;
import com.project.demo.rule.domain.Rule;
import com.project.demo.rule.repository.AdvanceAgreementRepository;
import com.project.demo.rule.repository.AdvanceRuleRepository;
import com.project.demo.rule.repository.AgreementRepository;
import com.project.demo.rule.repository.RuleRepository;
import com.project.demo.rule.service.RuleAgreementService;
import com.project.demo.ticket.domain.Ticket;
import com.project.demo.ticket.domain.TicketGrade;
import com.project.demo.ticket.repository.AdvanceTicketRepository;
import com.project.demo.ticket.repository.TicketRepository;
import com.project.demo.ticket.service.TicketService;
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
    protected AdvanceTicketRepository advanceTicketRepository;

    @Autowired
    protected RuleAgreementService ruleAgreementService;
    @Autowired
    protected RuleRepository ruleRepository;

    @Autowired
    protected AgreementRepository agreementRepository;


    @Autowired
    protected AdvanceRuleRepository advanceRuleRepository;
    @Autowired
    protected AdvanceAgreementRepository advanceAgreementRepository;
    @Autowired
    protected TicketService ticketService;

    @Autowired
    protected TicketRepository ticketRepository;


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
        ticketRepository.deleteAllInBatch();;
        projectRepository.deleteAllInBatch();
        agreementRepository.deleteAllInBatch();
        ruleRepository.deleteAllInBatch();
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

    public Ticket createTicket(Long memberId,Long projectId){
        Ticket t=Ticket.builder()
                .memberId(memberId)
                .projectId(projectId)
                .role(null)
                .build();

        t=ticketRepository.save(t);
        return t;
    }

    public Ticket createAdminTicket(Long memberId,Long projectId) {
        Ticket t = Ticket.builder()
                .memberId(memberId)
                .projectId(projectId)
                .role(null)
                .build();
        t.updateTicketGrade(TicketGrade.MASTER);
        t = ticketRepository.save(t);
        return t;
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
    public Rule createRule(Long id,String content){

        Rule rule=Rule.builder()
                .content(content)
                .projectId(id)
                .build();
        rule=ruleRepository.save(rule);
        return rule;
    }
    public Agreement createAgreement(Long projectId,Long ruleId,Long memberId){
        Agreement agreement=Agreement.builder()
                .projectId(projectId)
                .ruleId(ruleId)
                .memberId(memberId)
                .build();
        agreement=agreementRepository.save(agreement);
        return agreement;
    }

}
