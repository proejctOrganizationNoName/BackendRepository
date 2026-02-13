package com.project.demo.project.domain;

import lombok.Builder;
import lombok.Getter;

public class RequestDtos {


    @Getter
    public static class RequestCreateProjectDto{
        private String projectName;
        private String deadLine;
        private String imgUrl;
        @Builder
        public RequestCreateProjectDto(String projectName, String deadLine,String imgUrl) {
            this.projectName = projectName;
            this.deadLine = deadLine;
            this.imgUrl=imgUrl;
        }

    }

    @Getter
    public static class RequestUpdateProjectDto{
        private Long projectId;
        private String projectName;
        private String deadLine;
        private String imgUrl;
        @Builder
        public RequestUpdateProjectDto(Long projectId,String projectName, String deadLine,String imgUrl) {
            this.projectId=projectId;
            this.projectName = projectName;
            this.deadLine = deadLine;
            this.imgUrl=imgUrl;
        }
    }
}
