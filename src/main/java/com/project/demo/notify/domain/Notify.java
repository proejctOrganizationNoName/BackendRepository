package com.project.demo.notify.domain;


import com.project.demo.utility.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Notify extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notifyId;
    private Long projectId;
    private String content;
    private String title;
    private Boolean deleted=false;

    @Builder
    public Notify(String content,Long projectId,String title) {
        this.title=title;
        this.content = content;
        this.projectId=projectId;
    }
    public void updateDelete(){
        this.deleted=!this.deleted;
    }

    public void updateTitle(String title){
        this.title=title;
    }
    public void updateContent(String content){
        this.content=content;
    }
}
