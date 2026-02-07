package com.project.demo.rule.repository;

import com.project.demo.rule.domain.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RuleRepository extends JpaRepository<Rule,Long> {

    Optional<Rule> findByProjectId(Long projectId);

    Boolean existsByProjectId(Long projectId);
}
