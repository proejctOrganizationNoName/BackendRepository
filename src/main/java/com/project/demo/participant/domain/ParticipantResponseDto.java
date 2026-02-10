package com.project.demo.participant.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ParticipantResponseDto {

    @Getter
    @NoArgsConstructor
    public static final class ResponseParticipantMemberDto{
        private Long participantId;
        private Long memberId;
        private String name;


        @Builder
        public ResponseParticipantMemberDto(Long participantId, Long memberId,
                                    String name) {
            this.participantId = participantId;
            this.memberId = memberId;
            this.name = name;

        }
    }
}
