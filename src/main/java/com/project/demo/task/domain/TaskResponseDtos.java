package com.project.demo.task.domain;

import com.project.demo.participant.domain.ParticipantResponseDto;
import com.project.demo.task.service.TaskService;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.project.demo.participant.domain.ParticipantResponseDto.*;

public class TaskResponseDtos {

    @Getter
    @NoArgsConstructor
    public final static class TaskDto{
        private Long taskId;
        private String title;
        private String content;
        private String createDate;
        private String deadLine;
        private List<ResponseParticipantMemberDto> memberDtoList;
        private TaskState taskState;

        @Builder
        public TaskDto(Long taskId, String title, String content, String createDate
                , String deadLine, List<ResponseParticipantMemberDto> memberDtoList,TaskState taskState) {
            this.taskId = taskId;
            this.title = title;
            this.content = content;
            this.createDate = createDate;
            this.deadLine = deadLine;
            this.memberDtoList = memberDtoList;
            this.taskState=taskState;
        }
    }

    @Getter
    @NoArgsConstructor
    public final static class SimpleTaskDto{
        private Long taskId;
        private String title;
        private String deadLine;
        private String createDate;
        private TaskState taskState;

        @Builder
        public SimpleTaskDto(Long taskId, String title, String deadLine, String createDate, TaskState taskState) {
            this.taskId = taskId;
            this.title = title;
            this.deadLine = deadLine;
            this.createDate = createDate;
            this.taskState = taskState;
        }
    }
}
