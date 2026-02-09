package com.project.demo.proceeding.repositroy;


import com.project.demo.excpetion.CustomError;
import com.project.demo.member.domain.QMember;
import com.project.demo.proceeding.domain.*;
import com.project.demo.utility.CustomDateTimeFormat;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.project.demo.member.domain.QMember.*;
import static com.project.demo.proceeding.domain.ProceedingRequestDto.*;
import static com.project.demo.proceeding.domain.ProceedingResponseDto.*;
import static com.project.demo.proceeding.domain.QProceeding.*;
import static com.project.demo.proceeding.domain.QProceedingLog.*;

@Repository
@RequiredArgsConstructor
public class AdvanceProceedingRepository {

    private final ProceedingRepository proceedingRepository;
    private final ProceedingLogRepository proceedingLogRepository;
    private final JPAQueryFactory jpaQueryFactory;



    private Proceeding proceedingFindBYId(Long id){
        Optional<Proceeding> proceeding=proceedingRepository.findById(id);
        if(proceeding.isEmpty()||proceeding.get().getDeleted()){
            throw new CustomError("에러");
        }
        return proceeding.get();
    }

    private ProceedingLog proceedingLogFindBYId(Long id){
        Optional<ProceedingLog> proceedingLog=proceedingLogRepository.findById(id);
        if(proceedingLog.isEmpty()){
            throw new CustomError("에러");
        }
        return proceedingLog.get();
    }

    public Page<ProceedDto> getProceedList(Pageable pageable, Long projectId){

        List<ProceedDto> proceedings=jpaQueryFactory.select(Projections.constructor(
                ProceedDto.class,
                        proceeding.id,
                        proceeding.title,
                        proceeding.content,
                        Expressions.nullExpression(List.class),
                        proceeding.createDate.stringValue(),
                        Expressions.nullExpression(String.class)
                ))
                .from(proceeding)
                .where(proceeding.projectId.eq(projectId).and(proceeding.deleted.isFalse()))
                .orderBy(proceeding.createDate.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
        Long count=jpaQueryFactory.select(proceeding.count()
                )
                .from(proceeding)
                .where(proceeding.projectId.eq(projectId).and(proceeding.deleted.isFalse()))
                .fetch().getFirst();

        return new PageImpl<>(proceedings,pageable,count);
    }


    public void delProceed(Long id){
        Proceeding proceeding1=proceedingFindBYId(id);
        proceeding1.updateDeleted();
    }

    public ProceedMemberDto createNewProceedLog(Long proceedId,Long memberId,String name) {
        ProceedingLog proceedingLog1=ProceedingLog.builder()
                .memberId(memberId)
                .proceedingId(proceedId)
                .build();
        proceedingLog1= proceedingLogRepository.save(proceedingLog1);

        return ProceedMemberDto.builder()
                .name(name)
                .memberId(memberId)
                .proceedLogId(proceedId)
                .build();
    }

    public void delProceedLog(Long proceedLogId){
       ProceedingLog proceedingLog1=proceedingLogFindBYId(proceedLogId);
       proceedingLog1.updateDeleted();
    }



    public ProceedDto createProceeding(RequestProceedingCreate requestProceedingCreate){
        Proceeding p=Proceeding.builder()
                .projectId(requestProceedingCreate.getProjectId())
                .title(requestProceedingCreate.getTitle())
                .content(requestProceedingCreate.getContent())
                .build();
        p=proceedingRepository.save(p);
        Long proceedId=p.getId();
        List<ProceedingLog> proceedingLogList=requestProceedingCreate.getRequestProceedMemberDtos().stream().map(
                x->{
                    return ProceedingLog.builder()
                            .proceedingId(proceedId)
                            .memberId(x.getMemberId())
                            .build();
                }
        ).collect(Collectors.toList());
        proceedingLogRepository.saveAll(proceedingLogList);

        List<ProceedMemberDto> proceedMemberDtos=requestProceedingCreate.getRequestProceedMemberDtos().stream()
                .map(x->{
                    return ProceedMemberDto.builder()
                            .proceedLogId(x.getMemberId())
                            .memberId(x.getMemberId())
                            .name(x.getName())
                            .build();
                }).collect(Collectors.toList());


        return  ProceedDto.builder()
                .id(proceedId)
                .memberDtoList(proceedMemberDtos)
                .title(p.getTitle())
                .content(p.getContent())
                .createDate(CustomDateTimeFormat.parseServerTimeToClientFormat(p.getCreateDate()))
                .createDate(CustomDateTimeFormat.parseServerTimeToClientFormat(p.getUpdateDate()))
                .build();
    }

    public ProceedDto getProceeding(Long proceedId){
       Proceeding proceeding1=proceedingFindBYId(proceedId);
       List<ProceedMemberDto> proceedMemberDtos=jpaQueryFactory.select(
                       Projections.constructor(
                               ProceedMemberDto.class,
                               proceedingLog.id,
                               member.id,
                               member.nickName
                       )
               )
               .from(proceedingLog)
               .join(member)
               .on(member.id.eq(proceedingLog.memberId))
               .where(proceedingLog.proceedingId.eq(proceedId).and(proceedingLog.deleted.isFalse()))
               .fetch();

       return  ProceedDto.builder()
               .id(proceedId)
               .memberDtoList(proceedMemberDtos)
               .title(proceeding1.getTitle())
               .content(proceeding1.getContent())
               .createDate(CustomDateTimeFormat.parseServerTimeToClientFormat(proceeding1.getCreateDate()))
               .createDate(CustomDateTimeFormat.parseServerTimeToClientFormat(proceeding1.getUpdateDate()))
               .build();

    }



}
