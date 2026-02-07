package com.project.demo.ticket.domain;

import com.project.demo.utility.BaseTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(uniqueConstraints =  @UniqueConstraint(columnNames = {"memberId", "projectId"}))
public class Ticket extends BaseTime {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;
    private Long memberId;
    private Long projectId;

    private String role;

    @Enumerated(EnumType.STRING)
    private TicketGrade ticketGrade=TicketGrade.NORMAL;

    private Boolean deleted=false;


    @Builder
    public Ticket(Long memberId, Long projectId, String role) {
        this.memberId = memberId;
        this.projectId = projectId;
        this.role = role;
    }

    public void updateRole(String role){
        this.role=role;
    }

    public void updateTicketGrade(TicketGrade ticketGrade){
        this.ticketGrade=ticketGrade;
    }
    public void updateDeleted(){
        this.deleted=!this.deleted;
    }



}
