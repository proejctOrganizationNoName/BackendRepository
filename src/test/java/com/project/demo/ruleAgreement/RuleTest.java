package com.project.demo.ruleAgreement;

import com.project.demo.IntegralTestEnv;
import com.project.demo.rule.domain.Agreement;
import com.project.demo.rule.domain.Rule;
import com.project.demo.rule.domain.RuleAgreementRespones;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.project.demo.rule.domain.RuleAgreementRespones.*;
import static org.assertj.core.api.Assertions.*;

public class RuleTest extends IntegralTestEnv {



    Long projectId=12L;
    Long memberId=12L;

    Rule rule;

    @BeforeEach
    void setting(){
       rule=createRule(1L,"TEST");
    }


    @Test
    @DisplayName("rule 생성 테스트")
    void createRuleTest(){
        ruleAgreementService.createRule("test",projectId);
        assertThat(ruleRepository.existsByProjectId(projectId)).isTrue();
        assertThatThrownBy(()->ruleAgreementService.createRule("test",projectId))
                .hasMessage("이미 rule이 존재합니다");
    }

    @Test
    @DisplayName("agreement 생성 테스트")
    void createAgreementTest(){
        ruleAgreementService.updateOrCreateAgree(projectId,memberId,rule.getRuleId());
        Optional<Agreement> agreementOptional=agreementRepository.findByProjectIdAndMemberId(projectId,memberId);
        assertThat(agreementOptional.isEmpty()).isFalse();

        ruleAgreementService.updateOrCreateAgree(projectId,memberId,rule.getRuleId());
        agreementOptional=agreementRepository.findByProjectIdAndMemberId(projectId,memberId);
        assertThat(agreementOptional.get().getAgree()).isFalse();
    }

    @Test
    @DisplayName("rule 수정 테스트 및 agree update테스트")
    void updateRuleTest(){

        ruleAgreementService.updateOrCreateAgree(1L,1L,rule.getRuleId());
        ruleAgreementService.updateOrCreateAgree(1L,2L,rule.getRuleId());

        ruleAgreementService.updateRule(1L,"dsfdsfdf");

        assertThat(ruleRepository.findByProjectId(1L).get().getContent()).isEqualTo("dsfdsfdf");
        List<Agreement> agreementList=agreementRepository.findAllByRuleId(rule.getRuleId());
        agreementList.stream().forEach(x->{
            assertThat(x.getAgree()).isFalse();
        });

    }

    @Test
    @DisplayName("rule dto 가져오기 테스트")
    void getRuleDtoTest(){
        assertThatThrownBy(()->ruleAgreementService.getRuleDto(projectId))
                .hasMessage("만들어진 rule이 없습니다");

        assertThat(ruleAgreementService.getRuleDto(1L)).isNotEqualTo(null);
    }


}
