package com.project.demo.mail.controller;


import com.project.demo.mail.domain.RequestDtos;
import com.project.demo.mail.domain.RequestDtos.RequestAuthCodeDto;
import com.project.demo.mail.domain.RequestDtos.RequestCheckAuthCodeDto;
import com.project.demo.mail.service.MailService;
import com.project.demo.utility.ApiResponseCreator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail/request")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;
    @GetMapping("/make/authCode")
    public ResponseEntity<ApiResponseCreator<Object>> requestAuthCode(@RequestBody @Valid RequestAuthCodeDto requestAuthCodeDto){
        mailService.sendAuthCode(requestAuthCodeDto.getEmail(),requestAuthCodeDto.getEmailType());
        return ResponseEntity.ok(ApiResponseCreator.createResponse(null,"ok"));
    }

    @PostMapping("/check/authCode")
    public ResponseEntity<ApiResponseCreator<Object>> requestCheckAuthCode(@RequestBody @Valid RequestCheckAuthCodeDto requestCheckAuthCodeDto){
        mailService.checkAuthCode(requestCheckAuthCodeDto.getEmail(),requestCheckAuthCodeDto.getAuthCode());
        return ResponseEntity.ok(ApiResponseCreator.createResponse(null,"ok"));
    };


}
