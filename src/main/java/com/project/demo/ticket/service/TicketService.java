package com.project.demo.ticket.service;


import com.project.demo.excpetion.CustomError;
import com.project.demo.member.Service.SecurityMemberReadService;
import com.project.demo.member.domain.Member;
import com.project.demo.redis.RedisUserInfoService;
import com.project.demo.ticket.domain.Ticket;
import com.project.demo.ticket.domain.TicketGrade;
import com.project.demo.ticket.repository.AdvanceTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketService {

    private final AdvanceTicketRepository advanceTicketRepository;
    private final SecurityMemberReadService securityMemberReadService;
    public void createTicket(Long memberId,Long projectId){
        Optional<Ticket> t1=advanceTicketRepository.findByMemberIdAndTicketId(memberId,projectId);
        if(t1.isEmpty()){
            Ticket t=new Ticket(memberId,projectId,null);
            advanceTicketRepository.createTicket(t);
        } else if (t1.get().getDeleted()) {
            t1.get().updateDeleted();
        }
        else{
            throw new CustomError("티켓 생성중 에러 발생");
        }
    }

    public void changeMemberRole(String role,Long projectId,Long targetMemberId){
        Member member=securityMemberReadService.securityMemberRead();
        if(member.getId()==targetMemberId){
            advanceTicketRepository.updateMemberRole(role,projectId,targetMemberId);
        }
        else{
            advanceTicketRepository.updateByNotMySelf(member.getId(),targetMemberId,projectId,role);
        }
    }

    public void delTicket(Long targetMemberId,Long projectId,Long nextMaster){
        Member member=securityMemberReadService.securityMemberRead();
        Ticket t=advanceTicketRepository.findByMemberIdAndTicketId(member.getId(),projectId).get();
        if(t.getTicketGrade().equals(TicketGrade.MASTER)){
            if(member.getId()==targetMemberId){
                advanceTicketRepository.delByAdmin(nextMaster,projectId,t);
            }
            else{
                advanceTicketRepository.delByAdmin(targetMemberId,projectId);
            }
        }
        else{
            if(!member.getId().equals(targetMemberId)){
                throw new CustomError("관리자 권한이 필요합니다");
            }else {
                t.updateDeleted();
            }
        }
    }
}
