package com.project.demo.member.Service;


import com.project.demo.member.domain.Member;
import com.project.demo.security.domain.CustomUserDetail;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityMemberReadService {
    public Member securityMemberRead(){
        CustomUserDetail customUserDetail=
                (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
        return customUserDetail.getMember();
    }
}

