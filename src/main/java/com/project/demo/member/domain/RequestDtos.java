package com.project.demo.member.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public record RequestDtos() {

    @Getter
    @NoArgsConstructor
    public static class RequestChangeMemberInfo{
        @NotNull(message = "바꿀려는 회원정보의 카테고리를 넣어주세요")
        private MemberProperty memberProperty;

        @NotNull(message = "바꿀려는 회원정보의 값을 넣어주세요")
        private String value;

        @Builder
        public RequestChangeMemberInfo(MemberProperty memberProperty, String value) {
            this.memberProperty = memberProperty;
            this.value = value;
        }
    }


    @Getter
    @NoArgsConstructor
    public static class RequestMemberSignIn{

        @NotNull(message = "회원가입시 이메일은 필수값입니다")
        private String email;
        @NotNull(message = "회원가입시 비밀번호는 필수값입니다")
        private String password;
        private String imgUrl;
        @NotNull(message = "회원가입시 닉네임은 필수값입니다")
        private String nickName;
        @Builder
        public RequestMemberSignIn(String email, String password, String imgUrl, String nickName) {
            this.email = email;
            this.password = password;
            this.imgUrl = imgUrl;
            this.nickName = nickName;
        }
    }

}
