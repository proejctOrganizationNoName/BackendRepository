package com.project.demo.proceeding.domain;


import com.project.demo.utility.BaseTime;
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
public class Proceeding extends BaseTime {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long projectId;
    private String title;
    private Boolean deleted=false;
    private String content;
    @Builder
    public Proceeding(Long projectId, String title, String content) {
        this.projectId = projectId;
        this.title = title;
        this.content = content;
    }
    public void updateTitle(String title){
        this.title=title;
    }
    public void updateContent(String content){
        this.content=content;
    }
    public void updateDeleted(){
        this.deleted=!this.deleted;
    }
}
