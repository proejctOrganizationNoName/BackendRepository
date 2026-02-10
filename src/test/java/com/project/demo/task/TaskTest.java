package com.project.demo.task;

import com.project.demo.IntegralTestEnv;
import com.project.demo.member.domain.Member;
import com.project.demo.participant.domain.Participant;
import com.project.demo.participant.domain.ParticipantChange;
import com.project.demo.participant.domain.ParticipantRequestDto;
import com.project.demo.participant.domain.ParticipantType;
import com.project.demo.task.domain.Task;
import com.project.demo.task.domain.TaskRequestDtos;
import com.project.demo.task.domain.TaskResponseDtos;
import com.project.demo.task.domain.TaskState;
import com.project.demo.utility.CustomDateTimeFormat;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.project.demo.participant.domain.ParticipantRequestDto.*;
import static com.project.demo.task.domain.TaskRequestDtos.*;
import static com.project.demo.task.domain.TaskResponseDtos.*;
import static org.assertj.core.api.Assertions.*;

public class TaskTest extends IntegralTestEnv {


    Task task;
    Participant participant;

    Member m;


    @BeforeEach
    void setting(){
        task=createTask(1L,"test","test",LocalDateTime.now());
        participant=createParticipant(task.getId(),1L,ParticipantType.TASK);
        m=createMember(0L);
    }


    @Test
    @DisplayName("태스크 생성 조회 테스트")
    void testCreateTask(){
        RequestCreateTask requestCreateTask= RequestCreateTask.builder()
                .title("test")
                .content("test")
                .projectId(1L)
                .deadLine(CustomDateTimeFormat.parseServerTimeToClientFormat(LocalDateTime.now()))
                .memberDtoList(List.of(RequestParticipantMemberDto.builder()
                                .name("Test")
                                .memberId(1L)
                                .participantType(ParticipantType.TASK)
                        .build()))
                .build();
        TaskDto taskDto=taskService.createTask(requestCreateTask);
        assertThat(taskService.getTask(taskDto.getTaskId())).isNotEqualTo(null);
    }
    @Test
    @DisplayName("태스크 업데이트 테스트")
    void updateTaskTest(){
        RequestUpdateTask requestUpdateTask=RequestUpdateTask.builder()
                .taskId(task.getId())
                .taskState(TaskState.END)
                .title("change")
                .content("change")
                .requestParticipantChanges(List.of(RequestParticipantChange
                        .builder()
                                .participantChange(ParticipantChange.DELETE)
                                .targetId(task.getId())
                                .participantType(ParticipantType.TASK)
                                .participateId(participant.getId())
                        .build(),
                        RequestParticipantChange.builder()
                                .targetId(task.getId())
                                .memberId(m.getId())
                                .participantChange(ParticipantChange.ADD)
                                .participantType(ParticipantType.TASK)
                                .build()))
                .build();
        taskService.updateTask(requestUpdateTask);

        TaskDto taskDto=taskService.getTask(task.getId());




        assertThat(taskDto.getTitle()).isEqualTo("change");
        assertThat(taskDto.getContent()).isEqualTo("change");
        assertThat(taskDto.getTaskState()).isEqualTo(TaskState.END);
        assertThat(taskDto.getMemberDtoList().size()).isEqualTo(1);
        assertThat(taskDto.getMemberDtoList().get(0).getParticipantId()).isNotEqualTo(participant.getId());
    }
}
