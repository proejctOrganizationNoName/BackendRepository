package com.project.demo.ticket.repository;


import com.project.demo.excpetion.CustomError;
import com.project.demo.ticket.domain.Ticket;
import com.project.demo.ticket.domain.TicketGrade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AdvanceTicketRepository {

    private final TicketRepository ticketRepository;

    public void createTicket(Ticket ticket){
        ticketRepository.save(ticket);
    }

    public Optional<Ticket> findByMemberIdAndTicketId(Long memberId,Long projectId){
       return ticketRepository.findByMemberIdAndProjectId(memberId,projectId);
    }

    public void updateMemberRole(String role,Long projectId,Long targetMemberId){
        Optional<Ticket> t=findByMemberIdAndTicketId(targetMemberId,projectId);
        if(t.isEmpty()||t.get().getDeleted()){
            throw new CustomError("없는 티켓입니다");
        }
        t.get().updateRole(role);
    }

    public void updateByNotMySelf(Long memberId,Long targetMemberId,Long projectId,String role) {
        Ticket t = findByMemberIdAndTicketId(memberId, projectId).get();
        if (t.getTicketGrade().equals(TicketGrade.MASTER)) {
            updateMemberRole(role, projectId, targetMemberId);
        } else {
            throw new CustomError("관리자 권한이 필요합니다");

        }
    }
    public void delByAdmin(Long nextMaster,Long projectId,Ticket adminTicket){
        Optional<Ticket> next=findByMemberIdAndTicketId(nextMaster,projectId);
        if(next.isEmpty()||next.get().getDeleted()){
            throw new CustomError("적합지 않는 후보자입니다");
        }
        next.get().updateTicketGrade(TicketGrade.MASTER);
        adminTicket.updateTicketGrade(TicketGrade.NORMAL);
        adminTicket.updateDeleted();
    }
    public void delByAdmin(Long targetMemberId,Long projectId){
        Optional<Ticket> ticket=findByMemberIdAndTicketId(targetMemberId,projectId);
        if(ticket.isEmpty()||ticket.get().getDeleted()){
            throw new CustomError("없는 티켓입니다");
        }
        ticket.get().updateDeleted();
    }


}
