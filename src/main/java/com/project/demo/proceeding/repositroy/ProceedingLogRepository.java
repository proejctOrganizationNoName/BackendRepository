package com.project.demo.proceeding.repositroy;

import com.project.demo.proceeding.domain.Proceeding;
import com.project.demo.proceeding.domain.ProceedingLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProceedingLogRepository extends JpaRepository<ProceedingLog,Long> {


    List<ProceedingLog> findByProceedingId(Long id);
}
