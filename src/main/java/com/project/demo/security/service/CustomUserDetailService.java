package com.project.demo.security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.demo.member.Service.MemberService;
import com.project.demo.member.domain.Member;
import com.project.demo.member.domain.MemberType;
import com.project.demo.member.repository.MemberRepositoryImpl;
import com.project.demo.security.domain.CustomUserDetail;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private MemberRepositoryImpl memberRepository;


    public CustomUserDetailService(MemberRepositoryImpl memberRepository) {
        this.memberRepository=memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> member=memberRepository.findByEmail(username);
        if(member.isEmpty()||member.get().getDeleted()){
            throw new RuntimeException("존재하지 않는 회원입니다");
        }
        if(member.get().getMemberType()!= MemberType.LOCAL){
            throw new RuntimeException("일반 회원이 아닙니다");
        }
        return new CustomUserDetail(member.get());
    }
}
