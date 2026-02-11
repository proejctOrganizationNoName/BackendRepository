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

    @Getter
    @NoArgsConstructor
    public final static class RequestParticipantChange{
        private Long participateId;
        private ParticipantChange participantChange;
        private Long memberId;
        private String name;
        private Long targetId;
        private ParticipantType participantType;

        @Builder
        public RequestParticipantChange(Long participateId, ParticipantChange participantChange,
                                        Long memberId, String name, Long targetId, ParticipantType participantType) {
            this.participateId = participateId;
            this.participantChange = participantChange;
            this.memberId = memberId;
            this.name = name;
            this.targetId = targetId;
            this.participantType = participantType;
        }
    }
}
