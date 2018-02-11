package com.jmb.springfactory.unit.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.ThreadLocalRandom;

import static org.apache.commons.lang3.math.NumberUtils.LONG_ZERO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.GreaterOrEqual;
import org.mockito.internal.matchers.GreaterThan;
import org.mockito.runners.MockitoJUnitRunner;

import com.jmb.springfactory.dao.issue.IssueMySQLServiceImpl;
import com.jmb.springfactory.dao.issue.IssueRepository;

import lombok.val;

@RunWith(MockitoJUnitRunner.class)
public class IssueMySQLServiceTest {

  private static final int MAX_VALUE = 10;
  private static final int MIN_VALUE = 1;
  private static final String ANY_NIF = "12345678Z";
  private static final String EXISTS_USER_NIF = "99999999R";

  @InjectMocks
  private IssueMySQLServiceImpl issueMySQLServiceImpl;
  
  @Mock
  private IssueRepository issueRepository;
  
  @Test
  public void whenSearchByNifAndNotExistAnyIssueShouldReturnZero() {
    
    when(issueRepository.countByReviser_Nif(anyString())).thenReturn(LONG_ZERO);
    
    final val numberOfIssues = issueMySQLServiceImpl.countIssueByReviserNif(ANY_NIF);
    
    verify(issueRepository).countByReviser_Nif(ANY_NIF);
    assertEquals(LONG_ZERO, numberOfIssues);
  }
  
  @Test
  public void whenSearchByNifAndExistAnyIssueShouldReturnNumberGreaterThanZero() {
    
    final Long aRandomNumberGreaterThanZero = ThreadLocalRandom.current().nextLong(MIN_VALUE, MAX_VALUE);
    when(issueRepository.countByReviser_Nif(EXISTS_USER_NIF)).thenReturn(aRandomNumberGreaterThanZero);
    
    final val numberOfIssues = issueMySQLServiceImpl.countIssueByReviserNif(EXISTS_USER_NIF);
    
    verify(issueRepository).countByReviser_Nif(EXISTS_USER_NIF);
    assertThat(numberOfIssues, new GreaterThan<Long>(LONG_ZERO));
  }
}
