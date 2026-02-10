package com.project.demo.proceed;

import com.project.demo.IntegralTestEnv;
import com.project.demo.member.domain.Member;
import com.project.demo.participant.domain.Participant;
import com.project.demo.participant.domain.ParticipantRequestDto;
import com.project.demo.participant.domain.ParticipantType;
import com.project.demo.proceeding.domain.Proceeding;
import com.project.demo.project.domain.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

import static com.project.demo.participant.domain.ParticipantRequestDto.*;
import static com.project.demo.proceeding.domain.ProceedingRequestDto.*;
import static com.project.demo.proceeding.domain.ProceedingResponseDto.*;
import static org.assertj.core.api.Assertions.*;

public class ProceedTest extends IntegralTestEnv {



    Member m;
    Project p;

    Proceeding proceeding;

    Participant participant;


    @BeforeEach
    void setting(){
        m=createMember(0L);
        p=createProject();
        proceeding=createProceeding("test","Test",p.getId());
        participant=createParticipant(proceeding.getId(),m.getId(), ParticipantType.PROCEED);
    }

    @Test
    @DisplayName("회의록 생성 테스트")
    void createProceedTest(){
        List<RequestParticipantMemberDto> requestParticipantMemberDtos=new ArrayList<>();
        requestParticipantMemberDtos.add(RequestParticipantMemberDto.builder()
                        .name("test")
                        .memberId(m.getId())
                        .participantType(ParticipantType.PROCEED)
                .build());

        RequestProceedingCreate requestProceedingCreate= RequestProceedingCreate.builder()
                .title("test")
                .projectId(p.getId())
                .content("test")
                .memberIds(requestParticipantMemberDtos)
                .build();
        ProceedDto proceedDto=proceedService.createProceeding(requestProceedingCreate);

        ProceedDto proceedDto2=proceedService.getProceed(proceedDto.getId());
        assertThat(proceedDto2.getMemberDtoList().size()).isEqualTo(1);
        assertThat(proceedDto2.getMemberDtoList().getFirst().getMemberId()).isEqualTo(m.getId());


        RequestProceedList requestProceedList=RequestProceedList.builder()
                .offSet(1)
                .projectId(p.getId())
                .build();

        Page<ProceedDto> proceedDtos=proceedService.getProceedList(requestProceedList);
        assertThat(proceedDtos.getContent().size()).isEqualTo(2);

    }

    @Test
    @DisplayName("회의록,참여자 삭제,생성 테스트")
    void testDelProceedAndLogDelTest(){
        proceedService.delProceed(proceeding.getId());
        assertThatThrownBy(()->proceedService.getProceed(proceeding.getId()))
                .hasMessage("에러");

    }
}
