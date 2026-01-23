package com.project.demo.member.repository;


import com.project.demo.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl {

    private final MemberRepository memberRepository;

    public Member saveMember(Member m){
       return memberRepository.save(m);
    }

    public Boolean checkExist(String email){
        if(memberRepository.existsByEmail(email)){
           throw new RuntimeException("이미 존재하는 회원");
        }
        return true;
    }

    public Optional<Member> findByEmail(String email){
        return memberRepository.findByEmail(email);
    }
}
