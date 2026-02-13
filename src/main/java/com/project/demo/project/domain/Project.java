package com.project.demo.project.domain;


import com.project.demo.utility.BaseTime;
import com.project.demo.utility.CustomDateTimeFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Project extends BaseTime {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String projectName;
    private LocalDateTime deadLine;
    private String inviteCode;
    private Boolean deleted=false;
    private String imgUrl;
    @Builder
    public Project(String projectName, LocalDateTime deadLine, String inviteCode, String imgUrl) {
        this.projectName = projectName;
        this.deadLine = deadLine;
        this.inviteCode = inviteCode;
        this.imgUrl = imgUrl;
    }

    public void updateDeleted(){
        this.deleted=!this.deleted;
    }

    public void updateProjectName(String projectName){
        this.projectName=projectName;
    }

    public void updateDeadLine(String deadLine){
        this.deadLine= CustomDateTimeFormat.parseClientTimetoServerFormat(deadLine);
    }

    public void updateImgUrl(String url){
        this.imgUrl=url;
    }
}
