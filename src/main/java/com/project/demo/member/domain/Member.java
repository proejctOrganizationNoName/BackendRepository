package com.project.demo.member.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String nickName;
    private String password;
    private MemberType memberType;
    private String imgUrl;
    private Boolean deleted=false;

    @Builder
    public Member(String email, String nickName, String password, MemberType memberType, String imgUrl) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.memberType = memberType;
        this.imgUrl = imgUrl;
    }

    public void updatePassword(String password){
        this.password=password;
    }
    public void updateNickName(String nickName){
        this.nickName=nickName;
    }
    public void updateImgUrl(String imgUrl){
        this.imgUrl=imgUrl;
    }
    public void updateDeleted(){
        this.deleted=!this.deleted;
    }
}
