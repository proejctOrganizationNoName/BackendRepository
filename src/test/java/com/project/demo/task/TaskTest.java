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

import org.assertj.core.api.AbstractIntegerAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

import static com.project.demo.participant.domain.ParticipantRequestDto.*;
import static com.project.demo.task.domain.TaskRequestDtos.*;
import static com.project.demo.task.domain.TaskResponseDtos.*;
import static org.assertj.core.api.Assertions.*;

public class TaskTest extends IntegralTestEnv {


    Task task;
    Participant participant;

    Task task2;
    Member m;


    @BeforeEach
    void setting(){

        LocalDateTime now=LocalDateTime.now().plusMinutes(10L);
        task=createTask(1L,"test","test",now);
        m=createMember(0L);
        participant=createParticipant(task.getId(),m.getId(),ParticipantType.TASK);

        task2=createTask(1L,"test2","test2",now);
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


    @Test
    @DisplayName("삭제 테스트")
    void delTest(){
        taskService.delTask(task.getId());

        assertThatThrownBy(()->taskService.getTask(task.getId()))
                .hasMessage("없는 TASK 입니다");
    }

    @Test
    @DisplayName("태스크 조건부 검색 테스트")
    void taskConditionSearchTest(){
        RequestConditionSearch requestConditionSearch=RequestConditionSearch.builder()
                .offSet(1)
                .title("test")
                .build();
        Page<SimpleTaskDto> simpleTaskDtoPage=taskService.getTaskDtos(requestConditionSearch);
        assertThat(simpleTaskDtoPage.getContent().size()).isEqualTo(2);

        RequestConditionSearch requestConditionSearch1=RequestConditionSearch.builder()
                .offSet(1)
                .deadLine(CustomDateTimeFormat.parseServerTimeToClientFormat(LocalDateTime.now()))
                .build();
        Page<SimpleTaskDto> simpleTaskDtoPage1=taskService.getTaskDtos(requestConditionSearch1);
        assertThat(simpleTaskDtoPage1.getContent().size()).isEqualTo(0);

        RequestConditionSearch requestConditionSearch2=RequestConditionSearch.builder()
                .offSet(1)
                .memberId(m.getId())
                .build();
        Page<SimpleTaskDto> simpleTaskDtoPage2=taskService.getTaskDtos(requestConditionSearch2);
        assertThat(simpleTaskDtoPage2.getContent().size()).isEqualTo(1);
        assertThat(simpleTaskDtoPage.getContent().getFirst().getTaskId()).isEqualTo(task.getId());


        RequestConditionSearch requestConditionSearch3=RequestConditionSearch.builder()
                .offSet(1)
                .memberId(m.getId())
                .title("2")
                .build();
        Page<SimpleTaskDto> simpleTaskDtoPage3=taskService.getTaskDtos(requestConditionSearch3);
        assertThat(simpleTaskDtoPage3.getContent().size()).isEqualTo(0);

    }
}
