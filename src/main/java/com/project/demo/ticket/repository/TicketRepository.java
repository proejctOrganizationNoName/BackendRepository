package com.project.demo.ticket.repository;

import com.project.demo.ticket.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket,Long> {


    Optional<Ticket> findByMemberIdAndProjectId(Long memberId, Long ProjectId);

}
