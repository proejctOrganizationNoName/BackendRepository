package com.project.demo.notify.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RequestNotifyDto {

    @NoArgsConstructor
    @Getter
    public final static class RequestNotifyUpdateDto{
        private Long notifyId;
        private String content;
        private String title;
        @Builder
        public RequestNotifyUpdateDto(Long notifyId,String content, String title) {
            this.notifyId=notifyId;
            this.content = content;
            this.title = title;
        }
    }
    @NoArgsConstructor
    @Getter
    public final static class RequestNotifyList{
        private Long projectId;
        private int offSet;

        @Builder
        public RequestNotifyList(Long projectId, int offSet) {
            this.projectId = projectId;
            this.offSet = offSet;
        }

        public int provideOffset(){
            return this.offSet-1;
        }
    }
}
