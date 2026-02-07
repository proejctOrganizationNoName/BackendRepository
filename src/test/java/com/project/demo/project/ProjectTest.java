package com.project.demo.project;

import com.project.demo.IntegralTestEnv;
import com.project.demo.project.domain.Project;
import com.project.demo.project.domain.RequestDtos;
import com.project.demo.utility.CustomDateTimeFormat;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.project.demo.project.domain.RequestDtos.*;
@Transactional
public class ProjectTest extends IntegralTestEnv {


    Project p;

    @BeforeEach
    void setting(){
        p=createProject();
    }


    @Test
    @DisplayName("프로젝트 생성 테스트")
    void testCreateProject(){
        LocalDateTime nowDate=LocalDateTime.now();
        String now=CustomDateTimeFormat.parseServerTimeToClientFormat(nowDate);
        RequestCreateProjectDto requestCreateProjectDto=
                RequestCreateProjectDto.builder()
                        .projectName("test2")
                        .deadLine(now)
                        .build();

        projectService.createProject(requestCreateProjectDto);
        List<Project> projects=projectRepository.findAll();
;
        Assertions.assertThat(projects.getLast().getProjectName()).isEqualTo("test2");
        Assertions.assertThat(projects.getLast().getDeadLine()).isEqualTo(CustomDateTimeFormat.parseClientTimetoServerFormat(now));
    }
    @Test
    @DisplayName("프로젝트 업데이트 테스트")
    void testUpdateProject(){

        LocalDateTime nowDate=LocalDateTime.now();
        String now=CustomDateTimeFormat.parseServerTimeToClientFormat(nowDate);
        RequestUpdateProjectDto requestUpdateProjectDto=RequestUpdateProjectDto.builder()
                .projectName("test2")
                .projectId(p.getId())
                .deadLine(now)
                .build();
        projectService.updateProject(requestUpdateProjectDto);
        Project p1=projectRepository.findById(p.getId()).get();

        Assertions.assertThat(p1.getProjectName()).isEqualTo("test2");
        Assertions.assertThat(CustomDateTimeFormat
                .parseServerTimeToClientFormat(p1.getDeadLine())).isEqualTo(now);

    }


    @Test
    @DisplayName("프로젝트 초대 코드 업데이트")
    void updateInviteCode(){
        String old=p.getInviteCode();
        advanceProjectRepo.createNewInviteCode(p.getId());
        Assertions.assertThat(old)
                .isNotEqualTo(projectRepository.findById(p.getId()).get().getInviteCode());
    }


    @Test
    @DisplayName("프로젝트 삭제 테스트")
    void testProjectDel(){
        projectService.delProject(p.getId());
        Project p1=projectRepository.findById(p.getId()).get();

        Assertions.assertThat(p1.getDeleted()).isTrue();
    }
}
