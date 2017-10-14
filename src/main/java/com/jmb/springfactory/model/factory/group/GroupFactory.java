package com.jmb.springfactory.model.factory.group;

import com.jmb.springfactory.model.entity.WorkGroup;
import static com.jmb.springfactory.model.factory.group.GroupSamples.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class GroupFactory {

  public static WorkGroup createGroup(Integer id, String name, String startHour, String finishHour) {

    final WorkGroup group = new WorkGroup();
    group.setId(id);
    group.setName(name);
    group.setStartHour(startHour);
    group.setFinishHour(finishHour);
    
    return group;
  }
  
  public static WorkGroup createSampleDefaultGroup() {
    return createGroup(ID_GROUP_TEST_1, NAME_GROUP_TEST_1, START_HOUR_GROUP_TEST_1, FINISH_HOUR_GROUP_TEST_1);
  }
  
  public static Stream<WorkGroup> createStreamSampleDefaultGroup() {
    return Stream.of(createGroup(ID_GROUP_TEST_1, NAME_GROUP_TEST_1, START_HOUR_GROUP_TEST_1, FINISH_HOUR_GROUP_TEST_1),
        createGroup(ID_GROUP_TEST_2, NAME_GROUP_TEST_2, START_HOUR_GROUP_TEST_2, FINISH_HOUR_GROUP_TEST_2),
        createGroup(ID_GROUP_TEST_2, NAME_GROUP_TEST_2, START_HOUR_GROUP_TEST_2, FINISH_HOUR_GROUP_TEST_2));
  }
  
  public static List<WorkGroup> createListSampleDefaultGroup() {
    return createStreamSampleDefaultGroup().collect(Collectors.toList());
  }
}
