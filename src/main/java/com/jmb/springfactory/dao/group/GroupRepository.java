package com.jmb.springfactory.dao.group;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmb.springfactory.model.entity.WorkGroup;

@Repository
public interface GroupRepository extends JpaRepository<WorkGroup, Integer> {

}
