package com.project.demo.member.controller;


import com.project.demo.mail.domain.RequestDtos.RequestAuthCodeDto;
import com.project.demo.member.Service.MemberService;
import com.project.demo.member.domain.RequestDtos;
import com.project.demo.member.domain.RequestDtos.RequestChangeMemberInfo;
import com.project.demo.member.domain.RequestDtos.RequestMemberSignIn;
import com.project.demo.utility.ApiResponseCreator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member/request")
public class MemberController {


    private final MemberService memberService;

    @GetMapping("/check/exist")
    public ResponseEntity<ApiResponseCreator<Object>> requestCheckExist(@RequestBody @Valid RequestAuthCodeDto requestAuthCodeDto){
        memberService.checkMemberExist(requestAuthCodeDto.getEmail());
        return ResponseEntity.ok(ApiResponseCreator.createResponse(null,"ok"));
    }

    @PostMapping("/change/info")
    public ResponseEntity<ApiResponseCreator<Object>> requestChangePassword(@RequestBody @Valid RequestChangeMemberInfo requestChangeMemberInfo){
        memberService.changeMemberInfo(requestChangeMemberInfo);
        return ResponseEntity.ok(ApiResponseCreator.createResponse(null,"ok"));
    }

    @PostMapping("/signIn")
    public ResponseEntity<ApiResponseCreator<Object>> requestMemberSignIn(@RequestBody @Valid RequestMemberSignIn requestMemberSignIn){
        memberService.signInMember(requestMemberSignIn);
        return ResponseEntity.ok(ApiResponseCreator.createResponse(null,"ok"));
    }

}
