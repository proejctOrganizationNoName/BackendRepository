package com.project.demo.member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ResponseDtos {



    @Getter
    @NoArgsConstructor
    public static final class MemberDto{
        private Long id;
        private String email;
        private String nickName;
        private String createDate;
        private String imgUrl;

        @Builder
        public MemberDto(Long id, String email, String nickName, String createDate, String imgUrl) {
            this.id = id;
            this.email = email;
            this.nickName = nickName;
            this.createDate = createDate;
            this.imgUrl = imgUrl;
        }
    }
}
