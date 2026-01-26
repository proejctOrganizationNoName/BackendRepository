package com.project.demo.member;

import com.project.demo.IntegralTestEnv;
import com.project.demo.member.Service.SecurityMemberReadService;
import com.project.demo.member.domain.Member;
import com.project.demo.member.domain.MemberProperty;
import com.project.demo.member.domain.RequestDtos;
import com.project.demo.member.domain.RequestDtos.RequestChangeMemberInfo;
import com.project.demo.member.domain.RequestDtos.RequestMemberSignIn;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
        Assertions.assertThat( memberRepository.findByEmail("test").isPresent()).isEqualTo(true);
    }

    @Test
    @DisplayName("회원 데이터 수정 체크")
    void checkUpdateMemberInfo(){
        RequestChangeMemberInfo requestChangeMemberInfo=RequestChangeMemberInfo.builder()
                .memberProperty(MemberProperty.PASSWORD)
                .value("testpassword")
                .build();

        Mockito.when(securityMemberReadService.securityMemberRead())
                        .thenReturn(m1);

        memberService.changeMemberInfo(requestChangeMemberInfo);

        Member m=memberRepository.findByEmail(m1.getEmail()).get();

        Assertions.assertThat(passwordEncoder.matches("testpassword",m.getPassword()));
    }


}
