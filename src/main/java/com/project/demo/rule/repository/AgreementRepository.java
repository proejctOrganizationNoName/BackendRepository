package com.project.demo.rule.repository;

import com.project.demo.rule.domain.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface AgreementRepository extends JpaRepository<Agreement,Long> {


    Optional<Agreement> findByProjectIdAndMemberId(Long projectId,Long memberId);

    List<Agreement> findAllByRuleId(Long ruleId);


}
