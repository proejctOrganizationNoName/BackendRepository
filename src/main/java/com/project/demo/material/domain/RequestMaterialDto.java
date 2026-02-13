package com.project.demo.material.domain;

import com.project.demo.utility.ValidInterface;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RequestMaterialDto {



    @Getter
    @NoArgsConstructor
    public static final class RequestSaveMaterial implements ValidInterface {
        private Long taskId;
        private Long memberId;
        private String text;
        @Builder
        public RequestSaveMaterial(Long taskId, Long memberId, String text) {
            this.taskId = taskId;
            this.memberId = memberId;
            this.text = text;
        }

        @Override
        public Long provideId() {
            return this.taskId;
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
