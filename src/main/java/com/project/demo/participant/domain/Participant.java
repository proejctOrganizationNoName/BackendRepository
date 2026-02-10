package com.project.demo.participant.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long targetId;
    private Long memberId;
    private Boolean deleted=false;
    @Enumerated(EnumType.ORDINAL)
    private ParticipantType participantType;

    @Builder
    public Participant(Long targetId, Long memberId,ParticipantType participantType) {
        this.targetId = targetId;
        this.memberId = memberId;
        this.participantType=participantType;
    }

    public void updateDeleted(){
        this.deleted=!this.deleted;
    }
}
