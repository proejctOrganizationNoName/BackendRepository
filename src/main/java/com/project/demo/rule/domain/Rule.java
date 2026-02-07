package com.project.demo.rule.domain;

import com.project.demo.utility.BaseTime;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@Getter
public class Rule extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ruleId;
    @Column(unique = true)
    private Long projectId;
    private String content;
    private Boolean deleted=false;


    @Builder
    public Rule(Long projectId, String content) {
        this.projectId = projectId;
        this.content = content;
    }

    public void updateContent(String content){
        this.content=content;
    }

    public void updateDeleted(){
        this.deleted=!this.deleted;
    }
}
