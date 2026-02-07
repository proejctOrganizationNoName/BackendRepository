package com.project.demo.rule.service;


import com.nimbusds.oauth2.sdk.AbstractOptionallyAuthenticatedRequest;
import com.project.demo.excpetion.CustomError;
import com.project.demo.rule.domain.Agreement;
import com.project.demo.rule.domain.Rule;
import com.project.demo.rule.domain.RuleAgreementRespones;
import com.project.demo.rule.repository.AdvanceAgreementRepository;
import com.project.demo.rule.repository.AdvanceRuleRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.jaxb.mapping.spi.JaxbGenericIdGeneratorImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.project.demo.rule.domain.RuleAgreementRespones.*;

@Service
@RequiredArgsConstructor
@Transactional
public class RuleAgreementService {


    private final AdvanceRuleRepository advanceRuleRepository;
    private final AdvanceAgreementRepository agreementRepository;

    public void createRule(String content,Long projectId){

        if(!advanceRuleRepository.checkRuleExist(projectId)) {
            Rule rule = Rule.builder()
                    .projectId(projectId)
                    .content(content)
                    .build();
            advanceRuleRepository.createRule(rule);
            return;
        }
        throw new CustomError("이미 rule이 존재합니다");
    }
    public boolean agreementCheck(Long projectId,Long memberId){
        Optional<Agreement> agreement=agreementRepository.
                findByProjectIdAndMemberId(projectId,memberId);
        if(agreement.isEmpty()){
            return false;
        }
        return true;
    }

    public RuleDto getRuleDto(Long projectId){
       Rule rule= advanceRuleRepository.findByProjectId(projectId);
       return RuleDto.builder()
               .content(rule.getContent())
               .ruleId(rule.getRuleId())
               .build();
    }

    public void updateOrCreateAgree(Long projectId,Long memberId,Long ruleId){
        Optional<Agreement> agreement = agreementRepository.
                findByProjectIdAndMemberId(projectId, memberId);
        if(agreement.isEmpty()){
            Agreement agreements=Agreement.builder()
                    .ruleId(ruleId)
                    .memberId(memberId)
                    .projectId(projectId)
                    .build();
            agreementRepository.createAgreement(agreements);
            return;
        }
        else {
            agreement.get().updateAgree();
        }
    }
    public void updateRule(Long projectId,String content){
        Rule rule=advanceRuleRepository.findByProjectId(projectId);
        rule.updateContent(content);

        List<Agreement> agreementList=agreementRepository.findAllAgreement(rule.getRuleId());
        agreementList.stream().forEach(x->{
            x.updateAgree();
        });
    }

}
