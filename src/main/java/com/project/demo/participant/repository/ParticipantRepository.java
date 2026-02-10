package com.project.demo.participant.repository;

import com.project.demo.participant.domain.Participant;
import com.project.demo.participant.domain.ParticipantType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant,Long> {
}
