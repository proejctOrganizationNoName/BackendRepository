package com.project.demo.member;

import com.project.demo.IntegralTestEnv;
import com.project.demo.member.Service.SecurityMemberReadService;
import com.project.demo.member.domain.Member;
import com.project.demo.member.domain.MemberProperty;
import com.project.demo.member.domain.RequestDtos;
import com.project.demo.member.domain.RequestDtos.RequestChangeMemberInfo;
import com.project.demo.member.domain.RequestDtos.RequestMemberSignIn;
import com.project.demo.security.domain.CustomUserDetail;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class MemberTest extends IntegralTestEnv {


    Member m1;
    @BeforeEach
    void setting(){
            m1=createMember(1L);
    }



    @Test
    @DisplayName("이메일 중복 체크")
    void checkMemberExist(){
        Assertions.assertThatThrownBy(()->memberService.checkMemberExist(m1.getEmail()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("이미 존재하는 회원");
    }

    @Test
    @DisplayName("회원가입 되는지 체크")
    void signInMemberTest(){

        RequestMemberSignIn requestMemberSignIn=RequestMemberSignIn.builder()
                .email("test")
                .imgUrl("Test")
                .nickName("est")
                .password("test")
                .build();
        memberService.signInMember(requestMemberSignIn);
        Assertions.assertThat( memberRepositoryImpl.findByEmail("test").isPresent()).isEqualTo(true);
    }

    @Test
    @DisplayName("회원 데이터 수정 체크")
    void checkUpdateMemberInfo(){
        RequestChangeMemberInfo requestChangeMemberInfo=RequestChangeMemberInfo.builder()
                .memberProperty(MemberProperty.PASSWORD)
                .value("testpassword")
                .build();
        CustomUserDetail customUserDetail = new CustomUserDetail(m1);


        org.springframework.security.core.Authentication authentication =
                Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(customUserDetail);

        org.springframework.security.core.context.SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        memberService.changeMemberInfo(requestChangeMemberInfo);

        Member m=memberRepositoryImpl.findByEmail(m1.getEmail()).get();

        Assertions.assertThat(passwordEncoder.matches("testpassword",m.getPassword()));
    }


}
