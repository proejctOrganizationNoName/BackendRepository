package com.project.demo.participant.repository;


import com.project.demo.excpetion.CustomError;
import com.project.demo.participant.domain.Participant;
import com.project.demo.participant.domain.ParticipantType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AdvanceParticipantRepository {

    private final ParticipantRepository participantRepository;

    public Participant findById(Long id){
        Optional<Participant> participant=participantRepository
                .findById(id);
        if(participant.isEmpty()||participant.get().getDeleted()){
            throw new CustomError("없는 참가자");
        }
        return participant.get();
    }

    public Participant createParticipant(Long memberId,Long targetId,ParticipantType participantType){
        Participant participant= Participant.builder()
                .memberId(memberId)
                .targetId(targetId)
                .participantType(participantType)
                .build();
        return participantRepository.save(participant);
    }

    public void delParticipant(Long participantId){
        Participant participant=findById(participantId);
        participant.updateDeleted();
    }


    public List<Participant> saveAll(List<Participant> participantList){
        return participantRepository.saveAll(participantList);
    }
}
