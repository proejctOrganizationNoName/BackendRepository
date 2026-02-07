package com.project.demo.rule.repository;


import com.project.demo.excpetion.CustomError;
import com.project.demo.rule.domain.Agreement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AdvanceAgreementRepository {

    private final AgreementRepository agreementRepository;



    public void createAgreement(Agreement agreement){
        agreementRepository.save(agreement);
    }

    public Optional<Agreement> findByProjectIdAndMemberId(Long projectId, Long memberId){
        Optional<Agreement> agreementOptional=agreementRepository
                .findByProjectIdAndMemberId(projectId,memberId);
        return agreementOptional;
    }

    public List<Agreement> findAllAgreement(Long ruleId){
        List<Agreement> agreements=agreementRepository.findAllByRuleId(ruleId);
        return agreements;
    }
}
