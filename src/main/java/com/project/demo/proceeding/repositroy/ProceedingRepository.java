package com.project.demo.proceeding.repositroy;

import com.project.demo.proceeding.domain.Proceeding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProceedingRepository extends JpaRepository<Proceeding,Long> {
}
