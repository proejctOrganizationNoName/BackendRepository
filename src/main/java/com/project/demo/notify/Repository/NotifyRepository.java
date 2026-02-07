package com.project.demo.notify.Repository;

import com.project.demo.notify.domain.Notify;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotifyRepository extends JpaRepository<Notify,Long> {
}
