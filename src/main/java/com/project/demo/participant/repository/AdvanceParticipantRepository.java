package com.project.demo.participant.repository;


import com.project.demo.excpetion.CustomError;
import com.project.demo.participant.domain.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.project.demo.participant.domain.ParticipantRequestDto.*;
import static com.project.demo.participant.domain.QParticipant.participant;

@Repository
@RequiredArgsConstructor
public class AdvanceParticipantRepository {

    private final ParticipantRepository participantRepository;
    private final JPAQueryFactory jpaQueryFactory;


    public Participant findById(Long id){
        Optional<Participant> participant=participantRepository
                .findById(id);
        if(participant.isEmpty()||participant.get().getDeleted()){
            throw new CustomError("없는 참가자");
        }
        return participant.get();
    }

    public void updateParticipant(List<RequestParticipantChange> requestParticipantChanges){
        List<Participant> newParticipantList=new ArrayList<>();
        List<Long> delId=new ArrayList<>();
        requestParticipantChanges.stream().forEach(x->{
            if(x.getParticipantChange().equals(ParticipantChange.ADD)) {
                Participant participant = Participant.builder()
                        .memberId(x.getMemberId())
                        .targetId(x.getTargetId())
                        .participantType(x.getParticipantType())
                        .build();
                newParticipantList.add(participant);
            }
            else{
                delId.add(x.getParticipateId());
            }
        });
        saveAll(newParticipantList);
        jpaQueryFactory.update(participant)
                .where(participant.id.in(delId))
                .set(participant.deleted,true)
                .execute();

    }

    public List<Participant> saveAll(List<Participant> participantList){
        return participantRepository.saveAll(participantList);
    }
}
