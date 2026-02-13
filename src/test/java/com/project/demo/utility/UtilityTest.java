package com.project.demo.utility;

import com.project.demo.IntegralTestEnv;
import com.project.demo.material.domain.Material;
import com.project.demo.participant.domain.ParticipantChange;
import com.project.demo.participant.domain.ParticipantRequestDto;
import com.project.demo.participant.domain.ParticipantType;
import com.project.demo.task.domain.Task;
import com.project.demo.task.domain.TaskRequestDtos;
import com.project.demo.task.domain.TaskResponseDtos;
import com.project.demo.task.domain.TaskState;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.project.demo.task.domain.TaskRequestDtos.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UtilityTest extends IntegralTestEnv {


    Task task;
    Task task2;

    @BeforeEach
    void setting(){
        task=createTask(1L,"test","test", LocalDateTime.now());
        task2=createTask(1L,"test","test", LocalDateTime.now().plusHours(1L));
    }


    @Test
    @DisplayName("deadline 테스트")
    void taskDeadlineTest(){
        RequestUpdateTask requestUpdateTask= RequestUpdateTask.builder()
                .taskId(task.getId())
                .taskState(TaskState.END)
                .title("change")
                .content("change")
                .requestParticipantChanges(List.of(ParticipantRequestDto.RequestParticipantChange
                                .builder()
                                .participantChange(ParticipantChange.DELETE)
                                .targetId(task.getId())
                                .participantType(ParticipantType.TASK)
                                .participateId(1L)
                                .build(),
                        ParticipantRequestDto.RequestParticipantChange.builder()
                                .targetId(task.getId())
                                .memberId(1L)
                                .participantChange(ParticipantChange.ADD)
                                .participantType(ParticipantType.TASK)
                                .build()))
                .build();
        assertThatThrownBy(()->taskService.updateTask(requestUpdateTask))
                .hasMessage("마감 기한이 완료되서 불가능합니다");
    }
    @Test
    @DisplayName("deleted 테스트")
    void taskDelTest(){
        taskService.delTask(task2.getId());

        assertThat(task2.getDeadLine().isAfter(LocalDateTime.now())).isTrue();

        RequestUpdateTask requestUpdateTask= RequestUpdateTask.builder()
                .taskId(task.getId())
                .taskState(TaskState.END)
                .title("change")
                .content("change")
                .requestParticipantChanges(List.of(ParticipantRequestDto.RequestParticipantChange
                                .builder()
                                .participantChange(ParticipantChange.DELETE)
                                .targetId(task.getId())
                                .participantType(ParticipantType.TASK)
                                .participateId(1L)
                                .build(),
                        ParticipantRequestDto.RequestParticipantChange.builder()
                                .targetId(task.getId())
                                .memberId(1L)
                                .participantChange(ParticipantChange.ADD)
                                .participantType(ParticipantType.TASK)
                                .build()))
                .build();
        assertThatThrownBy(()->taskService.updateTask(requestUpdateTask))
                .hasMessage("마감 기한이 완료되서 불가능합니다");
    }

    @Test
    @DisplayName("없는 TASK 테스트")
    void taskEmptyTest(){

        assertThatThrownBy(()->taskService.getTask(100L))
                .hasMessage("없는 TASK 입니다");

        RequestUpdateTask requestUpdateTask= RequestUpdateTask.builder()
                .taskId(100L)
                .taskState(TaskState.END)
                .title("change")
                .content("change")
                .requestParticipantChanges(List.of(ParticipantRequestDto.RequestParticipantChange
                                .builder()
                                .participantChange(ParticipantChange.DELETE)
                                .targetId(task.getId())
                                .participantType(ParticipantType.TASK)
                                .participateId(1L)
                                .build(),
                        ParticipantRequestDto.RequestParticipantChange.builder()
                                .targetId(task.getId())
                                .memberId(1L)
                                .participantChange(ParticipantChange.ADD)
                                .participantType(ParticipantType.TASK)
                                .build()))
                .build();
        assertThatThrownBy(()->taskService.updateTask(requestUpdateTask))
                .hasMessage("마감 기한이 완료되서 불가능합니다");
    }
}
