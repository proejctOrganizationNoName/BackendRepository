package com.project.demo.ticket;

import com.project.demo.IntegralTestEnv;
import com.project.demo.member.domain.Member;
import com.project.demo.security.domain.CustomUserDetail;
import com.project.demo.ticket.domain.Ticket;
import com.project.demo.ticket.domain.TicketGrade;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.*;

public class ticketTest extends IntegralTestEnv {


    Ticket adminTicket;
    Member admin;
    Long projectId=1L;

    @BeforeEach
    void setting(){

        admin=createMember(1L);
        adminTicket=createAdminTicket(admin.getId(),projectId);
    }

    @Test
    @DisplayName("티켓 생성 테스트")
    void testTicketCreate(){
        ticketService.createTicket(1L,projectId);
        assertThat(ticketRepository.findByMemberIdAndProjectId(1L,projectId)).isPresent();
        assertThatThrownBy(()->ticketService.createTicket(1L,projectId))
                .hasMessage("티켓 생성중 에러 발생");
    }

    @Test
    @DisplayName("master의 다른사람 role 수정 테스트")
    void testTicketRoleFix(){
        Ticket t1=createTicket(12L,projectId);

        CustomUserDetail customUserDetail = new CustomUserDetail(admin);
        org.springframework.security.core.Authentication authentication =
                Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(customUserDetail);
        org.springframework.security.core.context.SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        ticketService.changeMemberRole("test",projectId,t1.getMemberId());
        assertThat(advanceTicketRepository.findByMemberIdAndTicketId(12L,projectId)
                .get().getRole()).isEqualTo("test");
    }
    @Test
    @DisplayName("보통사람의 다른사람 role 수정 테스트")
    void testNotMasterFix(){

        Member m1=createMember(0L);
        Ticket t1=createTicket(m1.getId(),projectId);

        CustomUserDetail customUserDetail = new CustomUserDetail(m1);
        org.springframework.security.core.Authentication authentication =
                Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(customUserDetail);
        org.springframework.security.core.context.SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        assertThatThrownBy(()->ticketService.changeMemberRole("test",projectId,admin.getId()))
                .hasMessage("관리자 권한이 필요합니다");

    }

    @Test
    @DisplayName("자기 자신 role 수정")
    void testFixRoleSelf(){
        Member m1=createMember(0L);
        Ticket t1=createTicket(m1.getId(),projectId);

        CustomUserDetail customUserDetail = new CustomUserDetail(m1);
        org.springframework.security.core.Authentication authentication =
                Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(customUserDetail);
        org.springframework.security.core.context.SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        ticketService.changeMemberRole("test",projectId, m1.getId());
        assertThat(ticketRepository.findByMemberIdAndProjectId(m1.getId(),projectId).get().getRole()).isEqualTo("test");
    }

    @Test
    @DisplayName("master의 타유저 삭제")
    void testMasterDelOther(){
        Member m1=createMember(0L);
        Ticket t1=createTicket(m1.getId(),projectId);

        CustomUserDetail customUserDetail = new CustomUserDetail(admin);
        org.springframework.security.core.Authentication authentication =
                Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(customUserDetail);
        org.springframework.security.core.context.SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        ticketService.delTicket(m1.getId(),projectId,null);
        assertThat(ticketRepository.findByMemberIdAndProjectId(m1.getId(),projectId).get().getDeleted()).isTrue();
    }
    @Test
    @DisplayName("타유저의 타유저 삭제")
    void testOtherDelOther(){
        Member m1=createMember(0L);
        Ticket t1=createTicket(m1.getId(),projectId);

        CustomUserDetail customUserDetail = new CustomUserDetail(m1);
        org.springframework.security.core.Authentication authentication =
                Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(customUserDetail);
        org.springframework.security.core.context.SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        assertThatThrownBy(()->ticketService.delTicket(admin.getId(),projectId,null))
                .hasMessage("관리자 권한이 필요합니다");
    }
    @Test
    @DisplayName("관리자 스스로 삭제")
    void delAdminSelf(){
        Member m1=createMember(0L);
        Ticket t1=createTicket(m1.getId(),projectId);

        CustomUserDetail customUserDetail = new CustomUserDetail(admin);
        org.springframework.security.core.Authentication authentication =
                Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(customUserDetail);
        org.springframework.security.core.context.SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        ticketService.delTicket(admin.getId(),projectId,m1.getId());
        assertThat(ticketRepository.findByMemberIdAndProjectId(m1.getId(),projectId).get().getTicketGrade()).isEqualTo(TicketGrade.MASTER);
        assertThat(ticketRepository.findByMemberIdAndProjectId(admin.getId(),projectId).get().getDeleted()).isTrue();
    }
    @Test
    @DisplayName("유저 스스로 삭제")
    void delUserSelf(){
        Member m1=createMember(0L);
        Ticket t1=createTicket(m1.getId(),projectId);

        CustomUserDetail customUserDetail = new CustomUserDetail(m1);
        org.springframework.security.core.Authentication authentication =
                Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(customUserDetail);
        org.springframework.security.core.context.SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        ticketService.delTicket(m1.getId(),projectId,null);
        assertThat(ticketRepository.findByMemberIdAndProjectId(m1.getId(),projectId).get().getDeleted()).isTrue();
    }

}
