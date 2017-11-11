package com.jmb.springfactory.dao.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmb.springfactory.model.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

}
