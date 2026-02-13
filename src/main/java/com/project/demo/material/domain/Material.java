package com.project.demo.material.domain;

import com.project.demo.utility.BaseTime;
import com.project.demo.utility.ClassCheck;
import com.querydsl.codegen.utils.model.ClassType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter

public class Material extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long refId;
    private Long memberId;
    private MaterialType materialType;
    private String key;
    private String title;
    private Boolean deleted;
    private ClassCheck classCheck;


    @Builder
    public Material(Long refId, Long memberId, MaterialType materialType, String key, String title, ClassCheck classCheck) {
        this.refId = refId;
        this.memberId = memberId;
        this.materialType = materialType;
        this.key = key;
        this.title = title;
        this.classCheck=classCheck;
    }

    public void updateDeleted(){
        this.deleted=!this.deleted;
    }
}
