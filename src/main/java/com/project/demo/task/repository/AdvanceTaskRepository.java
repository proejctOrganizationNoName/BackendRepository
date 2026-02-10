package com.project.demo.task.repository;


import com.project.demo.excpetion.CustomError;
import com.project.demo.member.domain.QMember;
import com.project.demo.participant.domain.Participant;
import com.project.demo.participant.domain.ParticipantType;
import com.project.demo.participant.domain.QParticipant;
import com.project.demo.participant.repository.AdvanceParticipantRepository;
import com.project.demo.task.domain.QTask;
import com.project.demo.task.domain.Task;
import com.project.demo.utility.CustomDateTimeFormat;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.project.demo.member.domain.QMember.*;
import static com.project.demo.participant.domain.ParticipantRequestDto.*;
import static com.project.demo.participant.domain.ParticipantResponseDto.*;
import static com.project.demo.participant.domain.QParticipant.*;
import static com.project.demo.task.domain.QTask.*;
import static com.project.demo.task.domain.TaskRequestDtos.*;
import static com.project.demo.task.domain.TaskResponseDtos.*;

@Repository
@RequiredArgsConstructor
public class AdvanceTaskRepository {
    private final TaskRepository taskRepository;
    private final AdvanceParticipantRepository advanceParticipantRepository;
    private final JPAQueryFactory jpaQueryFactory;


    public Task findById(Long id){
        Optional<Task> task=taskRepository.findById(id);
        if(task.isEmpty()||task.get().getDeleted()){
            throw new CustomError("없는 TASK 입니다");
        }
        return task.get();
    }
    public TaskDto createTask(RequestCreateTask requestCreateTask){
        Task task=Task.builder()
                .title(requestCreateTask.getTitle())
                .content(requestCreateTask.getContent())
                .projectId(requestCreateTask.getProjectId())
                .deadLine(CustomDateTimeFormat.parseClientTimetoServerFormat(requestCreateTask.getDeadLine()))
                .build();
        task=taskRepository.save(task);
        Long taskId=task.getId();

        List<Participant> participantList=requestCreateTask.getMemberDtoList().stream().map(x->{
            return Participant.builder()
                    .participantType(ParticipantType.TASK)
                    .targetId(taskId)
                    .memberId(x.getMemberId())
                    .build();
        }).collect(Collectors.toList());
        List<Participant> newParticipant=advanceParticipantRepository.saveAll(participantList);
        List<ResponseParticipantMemberDto> memberDtoList= IntStream.range(0, newParticipant.size())
                .mapToObj(x->{
            return ResponseParticipantMemberDto.builder()
                    .name(requestCreateTask.getMemberDtoList().get(x).getName())
                    .memberId(newParticipant.get(x).getMemberId())
                    .participantId(newParticipant.get(x).getId())
                    .build();
        }).collect(Collectors.toList());

        return TaskDto.builder()
                .title(task.getTitle())
                .content(task.getContent())
                .createDate(CustomDateTimeFormat.parseServerTimeToClientFormat(task.getCreateDate()))
                .deadLine(CustomDateTimeFormat.parseServerTimeToClientFormat(task.getDeadLine()))
                .taskId(taskId)
                .memberDtoList(memberDtoList)
                .taskState(task.getTaskState())
                .build();
    }
    public void delTask(Long taskId){
        Task task=findById(taskId);
        task.updateDeleted();
    }
    public void updateTask(RequestUpdateTask requestUpdateTask){
       Long taskId=requestUpdateTask.getTaskId();
       Task task=findById(taskId);
       if(requestUpdateTask.getTaskState()!=null){
           task.updateTaskState(requestUpdateTask.getTaskState());
       }
        if(requestUpdateTask.getContent()!=null){
            task.updateContent(requestUpdateTask.getContent());
        }
        if(requestUpdateTask.getTitle()!=null){
            task.updateTitle(requestUpdateTask.getTitle());
        }
        if(requestUpdateTask.getDeadLine()!=null){
            task.updateDeadLine(CustomDateTimeFormat
                    .parseClientTimetoServerFormat(requestUpdateTask.getDeadLine()));
        }
        List<RequestParticipantChange> requestParticipantChanges
                =requestUpdateTask.getRequestParticipantChanges();
        if(!requestParticipantChanges.isEmpty()){
            advanceParticipantRepository.updateParticipant(requestParticipantChanges);
        }
    }
    public TaskDto getTask(Long taskId){
        Task task1=findById(taskId);
        List<ResponseParticipantMemberDto> data=jpaQueryFactory.select(Projections.constructor(
                ResponseParticipantMemberDto.class,
                participant.id,
                participant.memberId,
                member.nickName
                ))
                .from(participant)
                .join(member)
                .on(participant.memberId.eq(member.id))
                .where(participant.deleted.isFalse()
                        .and(participant.participantType.eq(ParticipantType.TASK))
                        .and(participant.targetId.eq(taskId)))
                .fetch();

        return TaskDto.builder()
                .taskId(taskId)
                .content(task1.getContent())
                .title(task1.getTitle())
                .createDate(CustomDateTimeFormat.parseServerTimeToClientFormat(task1.getCreateDate()))
                .deadLine(CustomDateTimeFormat.parseServerTimeToClientFormat(task1.getDeadLine()))
                .memberDtoList(data)
                .taskState(task1.getTaskState())
                .build();
    }
}
