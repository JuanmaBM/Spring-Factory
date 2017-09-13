package com.jmb.springfactory.model.factory.group;

import com.jmb.springfactory.model.entity.Group;
import static com.jmb.springfactory.model.factory.group.GroupSamples.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class GroupFactory {

  public static Group createGroup(String id, String name, String startHour, String finishHour) {

    final Group group = new Group();
    group.setId(id);
    group.setName(name);
    group.setStartHour(startHour);
    group.setFinishHour(finishHour);
    
    return group;
  }
  
  public static Group createSampleDefaultGroup() {
    return createGroup(ID_GROUP_TEST_1, NAME_GROUP_TEST_1, START_HOUR_GROUP_TEST_1, FINISH_HOUR_GROUP_TEST_1);
  }
  
  public static Stream<Group> createStreamSampleDefaultGroup() {
    return Stream.of(createGroup(ID_GROUP_TEST_1, NAME_GROUP_TEST_1, START_HOUR_GROUP_TEST_1, FINISH_HOUR_GROUP_TEST_1),
        createGroup(ID_GROUP_TEST_2, NAME_GROUP_TEST_2, START_HOUR_GROUP_TEST_2, FINISH_HOUR_GROUP_TEST_2),
        createGroup(ID_GROUP_TEST_2, NAME_GROUP_TEST_2, START_HOUR_GROUP_TEST_2, FINISH_HOUR_GROUP_TEST_2));
  }
  
  public static List<Group> createListSampleDefaultGroup() {
    return createStreamSampleDefaultGroup().collect(Collectors.toList());
  }
}
