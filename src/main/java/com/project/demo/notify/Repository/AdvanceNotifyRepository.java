package com.project.demo.notify.Repository;


import com.project.demo.excpetion.CustomError;
import com.project.demo.notify.domain.Notify;
import com.project.demo.notify.domain.QNotify;
import com.project.demo.notify.domain.RequestNotifyDto;
import com.project.demo.notify.domain.ResponseNotifyDto;
import com.project.demo.utility.CustomDateTimeFormat;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.project.demo.notify.domain.QNotify.*;
import static com.project.demo.notify.domain.RequestNotifyDto.*;
import static com.project.demo.notify.domain.ResponseNotifyDto.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AdvanceNotifyRepository {

    private final NotifyRepository notifyRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public NotifyDto createNotify(String content,Long projectId,String title){
        Notify notify= Notify.builder()
                .title(title)
                .projectId(projectId)
                .content(content)
                .build();
        notify=notifyRepository.save(notify);

        return NotifyDto.builder()
                .notifyId(notify.getNotifyId())
                .title(title)
                .content(content)
                .createDate(CustomDateTimeFormat.parseServerTimeToClientFormat(notify.getCreateDate()))
                .build();

    }
    public Notify findById(Long notifyId){
        Optional<Notify> notifyOptional=notifyRepository.findById(notifyId);
        if(notifyOptional.isEmpty()||notifyOptional.get().getDeleted()){
            throw new CustomError("존재 하지않는 공지입니다");
        }
        return notifyOptional.get();
    }
    public void updateNotify(RequestNotifyUpdateDto requestNotifyUpdateDto){
        Notify notify=findById(requestNotifyUpdateDto.getNotifyId());
        notify.updateTitle(requestNotifyUpdateDto.getTitle());
        notify.updateContent(requestNotifyUpdateDto.getContent());
    }
    public void delNotify(Long notifyId){
        Notify notify=findById(notifyId);
        notify.updateDelete();
    }
    public Page<NotifyDto> getNotifyList(Long projectId, Pageable pageable){
        List<NotifyDto> notifyDtoList=jpaQueryFactory
                .select(Projections.constructor(NotifyDto.class,
                        notify.notifyId,
                        notify.title,
                        Expressions.nullExpression(String.class),
                        notify.createDate.stringValue(),
                        notify.updateDate.stringValue()
                        ))
                .from(notify)
                .where(notify.projectId.eq(projectId))
                .orderBy(notify.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        Long count=jpaQueryFactory
                .select(notify.count())
                .from(notify)
                .where(notify.projectId.eq(projectId))
                .fetchOne();

        return new PageImpl<>(notifyDtoList,pageable,count);
    }

}
