package com.github.emraxxor.fstack.demo.repositories;

import com.github.emraxxor.fstack.demo.entities.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLogRepository extends JpaRepository<UserLog, Long>  {
}
