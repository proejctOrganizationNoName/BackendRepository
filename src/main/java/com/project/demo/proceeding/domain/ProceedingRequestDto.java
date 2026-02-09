package com.project.demo.proceeding.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ProceedingRequestDto {


    @NoArgsConstructor
    @Getter
    public final static class RequestProceedingCreate {
        private Long projectId;
        private List<RequestProceedMemberDto> requestProceedMemberDtos;
        private String title;
        private String content;

        @Builder
        public RequestProceedingCreate(Long projectId, List<RequestProceedMemberDto> memberIds,
                                       String title, String content) {
            this.projectId = projectId;
            this.requestProceedMemberDtos = memberIds;
            this.title = title;
            this.content = content;
        }
    }

    @Getter
    @NoArgsConstructor
    public final static class RequestProceedMemberDto{
        private Long memberId;
        private String name;

        @Builder
        public RequestProceedMemberDto(Long memberId, String name) {
            this.memberId = memberId;
            this.name = name;
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
