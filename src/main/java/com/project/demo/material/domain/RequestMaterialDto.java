package com.project.demo.material.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RequestMaterialDto {



    @Getter
    @NoArgsConstructor
    public static final class RequestSaveMaterial{
        private Long taskId;
        private Long memberId;
        @Builder
        public RequestSaveMaterial(Long taskId, Long memberId) {
            this.taskId = taskId;
            this.memberId = memberId;
        }
    }

    @Getter
    @NoArgsConstructor
    public static final class RequestConditionSearchMaterial{
        private MaterialType materialType;
        private int offSet;
        private Long taskId;
        public int provideOffset(){
            return this.offSet-1;
        }
        @Builder
        public RequestConditionSearchMaterial(MaterialType materialType, int offSet, Long taskId) {
            this.materialType = materialType;
            this.offSet = offSet;
            this.taskId = taskId;
        }
    }
}
