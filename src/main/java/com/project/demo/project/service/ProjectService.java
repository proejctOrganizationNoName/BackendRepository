package com.project.demo.project.service;


import com.project.demo.member.Service.SecurityMemberReadService;
import com.project.demo.member.domain.Member;
import com.project.demo.project.domain.Project;
import com.project.demo.project.domain.RequestDtos;
import com.project.demo.project.domain.ResponseDtos;
import com.project.demo.project.repository.AdvanceProjectRepo;
import com.project.demo.utility.CustomDateTimeFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.project.demo.project.domain.RequestDtos.*;
import static com.project.demo.project.domain.ResponseDtos.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {

    private final AdvanceProjectRepo advanceProjectRepo;
    private final SecurityMemberReadService securityMemberReadService;

    public void createProject(RequestCreateProjectDto requestCreateProjectDto){
        LocalDateTime deadLine= CustomDateTimeFormat.parseClientTimetoServerFormat(
                requestCreateProjectDto.getDeadLine());
        Project p=Project.builder()
                .deadLine(deadLine)
                .projectName(requestCreateProjectDto.getProjectName())
                .inviteCode(UUID.randomUUID().toString())
                .build();
        advanceProjectRepo.createProject(p);
    }
    public void delProject(Long projectId){
        advanceProjectRepo.delProject(projectId);
    }
    public void updateProject(RequestUpdateProjectDto requestUpdateProjectDto){
            advanceProjectRepo.updateProject(requestUpdateProjectDto);
    }

    public List<SimpleProjectDto> getProjectList(){
        Member m=securityMemberReadService.securityMemberRead();
        return advanceProjectRepo.getProjectList(m.getId());
    }

}
