package com.project.demo.security.domain;

import com.project.demo.member.domain.MemberType;

public interface OAuth2Response {
    MemberType getProvider();
    String getEmail();

}
