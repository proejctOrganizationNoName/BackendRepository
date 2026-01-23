package com.project.demo.member.Service;

import com.project.demo.member.domain.Member;
import com.project.demo.member.domain.MemberProperty;
import com.project.demo.member.domain.MemberType;
import com.project.demo.member.domain.RequestDtos;
import com.project.demo.member.domain.RequestDtos.RequestChangeMemberInfo;
import com.project.demo.member.domain.RequestDtos.RequestMemberSignIn;
import com.project.demo.member.repository.MemberRepositoryImpl;
import com.project.demo.redis.RedisUserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {



    @Value("${spring.user.imgurl}")
    private String basicImgUrl;
    private final SecurityMemberReadService securityMemberReadService;
    private final MemberRepositoryImpl memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void checkMemberExist(String email){
        memberRepository.checkExist(email);
    }



    public void signInMember(RequestMemberSignIn requestMemberSignIn){
        Member member=Member.builder()
                .email(requestMemberSignIn.getEmail())
                .password(passwordEncoder.encode(requestMemberSignIn.getPassword()))
                .nickName(requestMemberSignIn.getNickName())
                .memberType(MemberType.LOCAL)
                .imgUrl(requestMemberSignIn.getImgUrl()==null ? basicImgUrl:requestMemberSignIn.getImgUrl())
                .build();
        memberRepository.saveMember(member);
    }


    public void changeMemberInfo(RequestChangeMemberInfo memberInfo){

        Member member=securityMemberReadService.securityMemberRead();
        switch (memberInfo.getMemberProperty()){
            case MemberProperty.NICKNAME -> {
                member.updateNickName(memberInfo.getValue());
            }
            case MemberProperty.PASSWORD -> {
                member.updatePassword(memberInfo.getValue());
            }
            case MemberProperty.IMGURL -> {
                member.updateImgUrl(memberInfo.getValue());
            }
            case MemberProperty.DELETED -> {
                member.updateDeleted();
            }
        }
        memberRepository.saveMember(member);
    }

}
