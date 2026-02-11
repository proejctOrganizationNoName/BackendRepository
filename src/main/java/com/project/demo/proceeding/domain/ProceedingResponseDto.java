package com.project.demo.proceeding.domain;

import com.project.demo.participant.domain.ParticipantResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.project.demo.participant.domain.ParticipantResponseDto.*;

public class ProceedingResponseDto {

    @Getter
    @NoArgsConstructor
    public final static class ProceedDto{
        private Long id;
        private String title;
        private String content;
        private List<ResponseParticipantMemberDto> memberDtoList;
        private String createDate;
        private String updateDate;

        @Builder
        public ProceedDto(Long id, String title, String content,
                          List<ResponseParticipantMemberDto> memberDtoList,
                          String createDate, String updateDate) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.memberDtoList = memberDtoList;
            this.createDate = createDate;
            this.updateDate = updateDate;
        }
    }
}
