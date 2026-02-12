package com.project.demo.task.domain;


import com.project.demo.utility.BaseTime;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Task extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long projectId;
    private String title;
    private String content;
    private Boolean deleted=false;
    private LocalDateTime deadLine;
    @Enumerated(EnumType.STRING)
    private TaskState taskState=TaskState.START;
    @Builder
    public Task(Long projectId, String title, String content, LocalDateTime deadLine) {
        this.projectId = projectId;
        this.title = title;
        this.content = content;
        this.deadLine = deadLine;
    }

    public void updateDeleted(){
        this.deleted=!this.deleted;
    }
    public void updateTaskState(TaskState taskState){
        this.taskState=taskState;
    }
    public void updateDeadLine(LocalDateTime deadLine){
        this.deadLine=deadLine;
    }
    public void updateContent(String content){
        this.content=content;
    }
    public void updateTitle(String title){
        this.title=title;
    }


}

