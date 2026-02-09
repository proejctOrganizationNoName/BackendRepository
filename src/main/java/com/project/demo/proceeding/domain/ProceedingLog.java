package com.project.demo.proceeding.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ProceedingLog {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long proceedingId;
    private Long memberId;
    private Boolean deleted=false;

    @Builder
    public ProceedingLog(Long proceedingId, Long memberId) {
        this.proceedingId = proceedingId;
        this.memberId = memberId;

    }

    public void updateDeleted(){
        this.deleted=!this.deleted;
    }
}
