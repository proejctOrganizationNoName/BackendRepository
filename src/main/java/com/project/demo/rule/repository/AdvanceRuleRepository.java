package com.project.demo.rule.repository;


import com.project.demo.excpetion.CustomError;
import com.project.demo.rule.domain.Rule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AdvanceRuleRepository{

    private final RuleRepository ruleRepository;


    public Rule findByProjectId(Long projectId){
        Optional<Rule> ruleOptional=ruleRepository.findByProjectId(projectId);
        if(ruleOptional.isEmpty()||ruleOptional.get().getDeleted()){
            throw new CustomError("만들어진 rule이 없습니다");
        }
        return ruleOptional.get();
    }

    public Boolean checkRuleExist(Long projectId){
        return ruleRepository.existsByProjectId(projectId);
    }

    public void createRule(Rule rule){
        ruleRepository.save(rule);
    }
}
