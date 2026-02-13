package com.project.demo.material.domain;

import com.project.demo.utility.ClassCheck;
import com.project.demo.utility.ValidInterface;
import com.querydsl.codegen.utils.model.ClassType;
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
        private MaterialType materialType;
        @Builder
        public RequestSaveMaterial(Long taskId, Long memberId, String text,MaterialType materialType) {
            this.taskId = taskId;
            this.memberId = memberId;
            this.text = text;
            this.materialType=materialType;
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
    @Getter
    @NoArgsConstructor
    public static final class RequestSimpleImgUpdate{
        private ClassCheck classCheck;
        private Long refId;
        private MaterialType materialType;
        @Builder
        public RequestSimpleImgUpdate(ClassCheck classCheck, MaterialType materialType, Long refId) {
            this.classCheck = classCheck;
            this.refId = refId;
            this.materialType=materialType;
        }
    }
}
