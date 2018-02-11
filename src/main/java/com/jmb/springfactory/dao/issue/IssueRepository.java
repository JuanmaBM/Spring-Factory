package com.jmb.springfactory.dao.issue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmb.springfactory.model.entity.Issue;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer>{

  public Long countByReviser_Nif(String nif);
}
