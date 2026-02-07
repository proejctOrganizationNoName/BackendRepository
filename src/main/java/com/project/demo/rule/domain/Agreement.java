package com.project.demo.rule.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(uniqueConstraints =  @UniqueConstraint(columnNames = {"projectId", "memberId"}))
public class Agreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agreementId;
    private Long ruleId;
    private Long memberId;
    private Long projectId;
    private Boolean agree=true;
    private Boolean deleted=false;


    @Builder
    public Agreement(Long ruleId, Long memberId, Long projectId) {
        this.ruleId = ruleId;
        this.memberId = memberId;
        this.projectId = projectId;
    }

    public void updateAgree(){
        this.agree=!this.agree;
    }

    public void updateDeleted(){
        this.deleted=!this.deleted;
    }
}
