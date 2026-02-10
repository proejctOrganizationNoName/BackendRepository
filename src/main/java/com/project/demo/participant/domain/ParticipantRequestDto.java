package com.project.demo.participant.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ParticipantRequestDto {
    @Getter
    @NoArgsConstructor
    public final static class RequestParticipantMemberDto{
        private Long memberId;
        private String name;
        private ParticipantType participantType;

        @Builder
        public RequestParticipantMemberDto(Long memberId, String name,
                                           ParticipantType participantType) {
            this.memberId = memberId;
            this.name = name;
            this.participantType = participantType;
        }
    }
}
