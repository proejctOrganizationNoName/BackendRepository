package com.project.demo.utility;


import com.project.demo.excpetion.CustomError;
import com.project.demo.task.domain.Task;
import com.project.demo.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@Component
@Aspect
@RequiredArgsConstructor
@Slf4j
public class ValidCheckAop {

    private final TaskRepository taskRepository;

    @Before("@annotation(UpdateValid)")
    public void validateDeleteOrDeadLine(JoinPoint joinPoint, ValidAnnotation updateValid){

        Object [] objectList=joinPoint.getArgs();
        switch (updateValid.type()){
            default -> {
                Long id=getId(objectList);
                Optional<Task> task=taskRepository.findById(id);
                if(task.isEmpty()||task.get().getDeleted()||task.get().getDeadLine().isBefore(LocalDateTime.now())){
                    throw new CustomError("마감 기한이 완료되서 불가능합니다");
                }
            }
        }
    }


    private Long getId(Object [] objects){
        return Arrays.stream(objects)
                .filter(arg -> arg instanceof ValidInterface)
                .map(arg -> ((ValidInterface) arg).provideId())
                .findFirst()
                .orElseThrow(() -> new CustomError("업데이트 대상 ID를 찾을 수 없습니다."));
    }

}
