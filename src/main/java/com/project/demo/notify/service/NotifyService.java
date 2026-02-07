package com.project.demo.notify.service;


import com.project.demo.notify.Repository.AdvanceNotifyRepository;
import com.project.demo.notify.domain.Notify;
import com.project.demo.utility.CustomDateTimeFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.demo.notify.domain.RequestNotifyDto.*;
import static com.project.demo.notify.domain.ResponseNotifyDto.*;

@Service
@RequiredArgsConstructor
@Transactional
public class NotifyService {

    private final AdvanceNotifyRepository advanceNotifyRepository;

    public NotifyDto createNotify(Long projectId, String content, String title){
        return advanceNotifyRepository.createNotify(content,projectId,title);
    }
    public void delNotify(Long notifyId){
        advanceNotifyRepository.delNotify(notifyId);
    }
    public void updateNotify(RequestNotifyUpdateDto requestNotifyUpdateDto){
        advanceNotifyRepository.updateNotify(requestNotifyUpdateDto);
    }

    public NotifyDto findById(Long notifyId){
        Notify notify=advanceNotifyRepository.findById(notifyId);
        NotifyDto notifyDto=NotifyDto.builder()
                .notifyId(notifyId)
                .content(notify.getContent())
                .title(notify.getTitle())
                .updateDate(notify.getUpdateDate().toString())
                .createDate(notify.getCreateDate().toString())
                .build();
        notifyDto.parseDateTime();

        return notifyDto;
    }


    public Page<NotifyDto> getNotifyList(RequestNotifyList notifyList){
        PageRequest pageRequest=PageRequest.of(notifyList.provideOffset(),10);
        return advanceNotifyRepository.getNotifyList(notifyList.getProjectId(),pageRequest);
    }
}
