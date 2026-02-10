package com.project.demo.proceeding.domain;

import com.project.demo.participant.domain.ParticipantRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.project.demo.participant.domain.ParticipantRequestDto.*;

public class ProceedingRequestDto {


    @NoArgsConstructor
    @Getter
    public final static class RequestProceedingCreate {
        private Long projectId;
        private List<RequestParticipantMemberDto> requestProceedMemberDtos;
        private String title;
        private String content;

        @Builder
        public RequestProceedingCreate(Long projectId, List<RequestParticipantMemberDto> memberIds,
                                       String title, String content) {
            this.projectId = projectId;
            this.requestProceedMemberDtos = memberIds;
            this.title = title;
            this.content = content;
        }
    }

    @Getter
    @NoArgsConstructor
    public final static class RequestProceedList{
        private int offSet;
        private Long projectId;
        @Builder
        public RequestProceedList(int offSet, Long projectId) {
            this.offSet = offSet;
            this.projectId = projectId;
        }
        public int provideOffset(){
            return this.offSet-1;
        }
    }
}
