package com.project.demo.task.service;


import com.project.demo.task.domain.TaskRequestDtos;
import com.project.demo.task.domain.TaskResponseDtos;
import com.project.demo.task.repository.AdvanceTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.demo.task.domain.TaskRequestDtos.*;
import static com.project.demo.task.domain.TaskResponseDtos.*;

@RequiredArgsConstructor
@Service
@Transactional
public class TaskService {

    private final AdvanceTaskRepository advanceTaskRepository;

    public TaskDto getTask(Long taskId){
        return advanceTaskRepository.getTask(taskId);
    }
    public TaskDto createTask(RequestCreateTask requestCreateTask){
        return advanceTaskRepository.createTask(requestCreateTask);
    }
    public void updateTask(RequestUpdateTask requestUpdateTask){
        advanceTaskRepository.updateTask(requestUpdateTask);
    }
    public void delTask(Long taskId){
        advanceTaskRepository.delTask(taskId);
    }

    public Page<SimpleTaskDto> getTaskDtos(RequestConditionSearch requestConditionSearch){
        return advanceTaskRepository.conditionSearch(requestConditionSearch);
    }
}
