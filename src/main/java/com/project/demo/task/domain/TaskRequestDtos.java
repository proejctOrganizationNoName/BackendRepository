package com.project.demo.task.domain;

import com.project.demo.participant.domain.ParticipantRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.project.demo.participant.domain.ParticipantRequestDto.*;

public class TaskRequestDtos {


    @Getter
    @NoArgsConstructor
    public final static class RequestCreateTask{
        private String title;
        private String content;
        private String deadLine;
        private Long projectId;
        private List<RequestParticipantMemberDto> memberDtoList;
        @Builder
        public RequestCreateTask(String title, String content, String deadLine,
                                 Long projectId, List<RequestParticipantMemberDto> memberDtoList) {
            this.title = title;
            this.content = content;
            this.deadLine = deadLine;
            this.projectId = projectId;
            this.memberDtoList = memberDtoList;
        }
    }

    @Getter
    @NoArgsConstructor
    public final static class RequestUpdateTask{
        private Long taskId;
        private String title;
        private String content;
        private String deadLine;
        private TaskState taskState;
        private List<RequestParticipantChange> requestParticipantChanges;
        @Builder
        public RequestUpdateTask(Long taskId, String title, String content, String deadLine,
                                 TaskState taskState, List<RequestParticipantChange> requestParticipantChanges) {
            this.taskId = taskId;
            this.title = title;
            this.content = content;
            this.deadLine = deadLine;
            this.taskState = taskState;
            this.requestParticipantChanges = requestParticipantChanges;
        }
    }

    @Getter
    @NoArgsConstructor
    public final static class RequestConditionSearch{
        private Long memberId;
        private String deadLine;
        private String title;
        private int offSet;

        @Builder
        public RequestConditionSearch(Long memberId, String deadLine, String title, int offSet) {
            this.memberId = memberId;
            this.deadLine = deadLine;
            this.title=title;
            this.offSet = offSet;
        }

        public int provideOffset(){
            return this.offSet-1;
        }
    }
}
