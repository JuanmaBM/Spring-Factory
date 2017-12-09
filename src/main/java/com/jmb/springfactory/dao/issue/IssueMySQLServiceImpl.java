package com.jmb.springfactory.dao.issue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMySQLServiceImpl;
import com.jmb.springfactory.model.entity.Issue;

@Service
public class IssueMySQLServiceImpl extends GenericMySQLServiceImpl<Issue, Integer> implements IssueMySQLService {

  @Autowired
  private IssueRepository issueRepository;

  @Override
  public JpaRepository<Issue, Integer> getRepository() {
    return issueRepository;
  }

}
