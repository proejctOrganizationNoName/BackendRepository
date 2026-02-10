package com.project.demo.proceeding.repositroy;


import com.project.demo.excpetion.CustomError;
import com.project.demo.participant.domain.Participant;
import com.project.demo.participant.domain.ParticipantType;
import com.project.demo.participant.repository.AdvanceParticipantRepository;
import com.project.demo.proceeding.domain.*;
import com.project.demo.utility.CustomDateTimeFormat;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static com.project.demo.member.domain.QMember.*;
import static com.project.demo.participant.domain.ParticipantResponseDto.*;
import static com.project.demo.participant.domain.QParticipant.*;
import static com.project.demo.proceeding.domain.ProceedingRequestDto.*;
import static com.project.demo.proceeding.domain.ProceedingResponseDto.*;
import static com.project.demo.proceeding.domain.QProceeding.*;

@Repository
@RequiredArgsConstructor
public class AdvanceProceedingRepository {

    private final ProceedingRepository proceedingRepository;
    private final AdvanceParticipantRepository advanceParticipantRepository;
    private final JPAQueryFactory jpaQueryFactory;

    private Proceeding proceedingFindBYId(Long id){
        Optional<Proceeding> proceeding=proceedingRepository.findById(id);
        if(proceeding.isEmpty()||proceeding.get().getDeleted()){
            throw new CustomError("에러");
        }
        return proceeding.get();
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
    public ProceedDto createProceeding(RequestProceedingCreate requestProceedingCreate){
        Proceeding p=Proceeding.builder()
                .projectId(requestProceedingCreate.getProjectId())
                .title(requestProceedingCreate.getTitle())
                .content(requestProceedingCreate.getContent())
                .build();
        p=proceedingRepository.save(p);
        Long proceedId=p.getId();
        List<Participant> participantList=requestProceedingCreate.getRequestProceedMemberDtos().stream().map(
                x->{
                    return Participant.builder()
                            .targetId(proceedId)
                            .memberId(x.getMemberId())
                            .participantType(ParticipantType.PROCEED)
                            .build();
                }
        ).collect(Collectors.toList());
        List<Participant> newList=advanceParticipantRepository.saveAll(participantList);

        List<ResponseParticipantMemberDto> proceedMemberDtos=IntStream.range(0,participantList.size())
                .mapToObj(i->{
                    return ResponseParticipantMemberDto.builder()
                            .memberId(newList.get(i).getMemberId())
                            .name(requestProceedingCreate.getRequestProceedMemberDtos().get(i).getName())
                            .participantId(newList.get(i).getId())
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
       List<ResponseParticipantMemberDto> proceedMemberDtos=jpaQueryFactory.select(
                       Projections.constructor(
                               ResponseParticipantMemberDto.class,
                               participant.id,
                               member.id,
                               member.nickName
                       )
               )
               .from(participant)
               .join(member)
               .on(member.id.eq(participant.memberId))
               .where(participant.targetId.eq(proceedId).and(participant.deleted.isFalse())
                       .and(participant.participantType.eq(ParticipantType.PROCEED)))
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
