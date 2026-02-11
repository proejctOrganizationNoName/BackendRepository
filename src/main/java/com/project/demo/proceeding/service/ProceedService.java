package com.project.demo.proceeding.service;


import com.project.demo.proceeding.repositroy.AdvanceProceedingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.project.demo.participant.domain.ParticipantResponseDto.*;
import static com.project.demo.proceeding.domain.ProceedingRequestDto.*;
import static com.project.demo.proceeding.domain.ProceedingResponseDto.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ProceedService {


    private final AdvanceProceedingRepository advanceProceedingRepository;


    public ProceedDto createProceeding(RequestProceedingCreate requestProceedingCreate){
       return advanceProceedingRepository.createProceeding(requestProceedingCreate);
    }

    public Page<ProceedDto> getProceedList(RequestProceedList requestProceedList){

        PageRequest pageRequest=PageRequest.of(requestProceedList.provideOffset(),10);
        return advanceProceedingRepository.getProceedList(pageRequest,requestProceedList.getProjectId());
    }

    public ProceedDto getProceed(Long proceedId){
        return advanceProceedingRepository.getProceeding(proceedId);
    }

    public void delProceed(Long proceedId){
        advanceProceedingRepository.delProceed(proceedId);
    }
}
