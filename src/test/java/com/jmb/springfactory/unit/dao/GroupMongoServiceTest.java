package com.jmb.springfactory.unit.dao;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Example;

import com.jmb.springfactory.dao.group.GroupMongoServiceImpl;
import com.jmb.springfactory.dao.group.GroupRepository;
import com.jmb.springfactory.model.entity.WorkGroup;

@RunWith(MockitoJUnitRunner.class)
public class GroupMongoServiceTest {

  @InjectMocks
  private GroupMongoServiceImpl groupMongoService;
  
  @Mock
  private GroupRepository groupRepository;
  
  @Test
  @SuppressWarnings("unchecked")
  public void whenSearchGroupByNameShoulInvokeFindAll() {
    
    when(groupRepository.findAll(any(Example.class))).thenReturn(new ArrayList<WorkGroup>());
    
    groupMongoService.findByName(anyString());
    
    verify(groupRepository).findAll(any(Example.class));
  }
}
