package com.project.demo.project.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ResponseDtos {

    public static class ProjectDto{
        private Long id;
        private String projectName;
        private String inviteCode;
        private String deadLine;
        private String createDate;
        @Builder
        public ProjectDto(Long id, String projectName, String inviteCode,
                          String deadLine,String createDate) {
            this.id = id;
            this.projectName = projectName;
            this.inviteCode = inviteCode;
            this.deadLine = deadLine;
            this.createDate=createDate;
        }
    }


    @Getter
    @NoArgsConstructor
    public static final class SimpleProjectDto{
        private Long id;
        private String projectName;

        @Builder
        public SimpleProjectDto(Long id, String projectName) {
            this.id = id;
            this.projectName = projectName;
        }
    }
}
