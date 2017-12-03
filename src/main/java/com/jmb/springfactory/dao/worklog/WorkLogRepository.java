package com.jmb.springfactory.dao.worklog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmb.springfactory.model.entity.WorkLog;

@Repository
public interface WorkLogRepository extends JpaRepository<WorkLog, Integer> {

}
