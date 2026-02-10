package com.project.demo.member.Service;

import com.project.demo.excpetion.CustomError;
import com.project.demo.member.domain.Member;
import com.project.demo.member.domain.MemberProperty;
import com.project.demo.member.domain.MemberType;
import com.project.demo.member.domain.RequestDtos.RequestChangeMemberInfo;
import com.project.demo.member.domain.RequestDtos.RequestMemberSignIn;
import com.project.demo.member.domain.ResponseDtos;
import com.project.demo.member.repository.MemberRepositoryAdvance;
import com.project.demo.redis.RedisUserInfoService;
import com.project.demo.utility.CustomDateTimeFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.project.demo.member.domain.ResponseDtos.*;


@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {



    @Value("${spring.user.imgurl}")
    private String basicImgUrl;
    private final SecurityMemberReadService securityMemberReadService;
    private final MemberRepositoryAdvance memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisUserInfoService redisUserInfoService;

    public void checkMemberExist(String email){
        memberRepository.checkExist(email);
    }

    public void signInMember(RequestMemberSignIn requestMemberSignIn){
        if(redisUserInfoService.authCodePassed(requestMemberSignIn.getEmail())) {
            Member member = Member.builder()
                    .email(requestMemberSignIn.getEmail())
                    .password(passwordEncoder.encode(requestMemberSignIn.getPassword()))
                    .nickName(requestMemberSignIn.getNickName())
                    .memberType(MemberType.LOCAL)
                    .imgUrl(requestMemberSignIn.getImgUrl() == null ? basicImgUrl : requestMemberSignIn.getImgUrl())
                    .build();
            memberRepository.saveMember(member);
        }
        else{
            throw new CustomError("인증 단계를 아직 거치지 못했습니다");
        }
    }
    public void changeMemberInfo(RequestChangeMemberInfo memberInfo){

        Member member=securityMemberReadService.securityMemberRead();
        switch (memberInfo.getMemberProperty()){
            case MemberProperty.NICKNAME -> {
                member.updateNickName(memberInfo.getValue());
            }
            case MemberProperty.PASSWORD -> {
                member.updatePassword(passwordEncoder.encode(memberInfo.getValue()));
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
    public MemberDto getMemberInfo(){
        Member member=securityMemberReadService.securityMemberRead();
        return MemberDto.builder()
                .id(member.getId())
                .createDate(CustomDateTimeFormat.parseServerTimeToClientFormat(member.getCreateDate()))
                .email(member.getEmail())
                .nickName(member.getNickName())
                .imgUrl(member.getImgUrl())
                .build();
    }

    public void delMember(Long memberId){
       Optional<Member> memberOptional= memberRepository.findById(memberId);
       if(memberOptional.isEmpty()||memberOptional.get().getDeleted()){
           throw new CustomError("없는 회원입니다");
       }
       memberOptional.get().updateDeleted();
    }

}
