package com.project.demo.project.repository;


import com.project.demo.excpetion.CustomError;
import com.project.demo.project.domain.Project;
import com.project.demo.project.domain.RequestDtos;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.project.demo.project.domain.RequestDtos.*;

@Repository
@RequiredArgsConstructor
public class AdvanceProjectRepo {

    private final ProjectRepository projectRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public Project createProject(Project p){
        return projectRepository.save(p);
    }
    public Project findProjectById(Long id){
        Optional<Project> p=projectRepository.findById(id);
        if(p.isEmpty()||p.get().getDeleted()){
            throw new CustomError("이미 종료/삭제된 프로젝트 입니다");
        }
        return p.get();
    }
    public String createNewInviteCode(Long id){
        Project p=findProjectById(id);
        p.updateInviteCode();
        return p.getInviteCode();
    }
    public void delProject(Long id){
        Project p=findProjectById(id);
        p.updateDeleted();
    }
    public void updateProject(RequestUpdateProjectDto requestUpdateProjectDto){
        Project p=findProjectById(requestUpdateProjectDto.getProjectId());
        if(requestUpdateProjectDto.getDeadLine()!=null){
            p.updateDeadLine(requestUpdateProjectDto.getDeadLine());
        }
        if(requestUpdateProjectDto.getProjectName()!=null){
            p.updateProjectName(requestUpdateProjectDto.getProjectName());
        }
    }


}
