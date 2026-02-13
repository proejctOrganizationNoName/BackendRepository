package com.project.demo.material.domain;

import com.project.demo.utility.BaseTime;
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
    private Long taskId;
    private Long memberId;
    private MaterialType materialType;
    private String key;
    private String title;
    private Boolean deleted;

    @Builder
    public Material(Long taskId, Long memberId, MaterialType materialType, String key, String title) {
        this.taskId = taskId;
        this.memberId = memberId;
        this.materialType = materialType;
        this.key = key;
        this.title = title;
    }

    public void updateDeleted(){
        this.deleted=!this.deleted;
    }
}
