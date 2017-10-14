package com.jmb.springfactory.model.factory.group;

import static com.jmb.springfactory.model.factory.group.GroupSamples.FINISH_HOUR_GROUP_TEST_1;
import static com.jmb.springfactory.model.factory.group.GroupSamples.FINISH_HOUR_GROUP_TEST_2;
import static com.jmb.springfactory.model.factory.group.GroupSamples.ID_GROUP_TEST_1;
import static com.jmb.springfactory.model.factory.group.GroupSamples.ID_GROUP_TEST_2;
import static com.jmb.springfactory.model.factory.group.GroupSamples.NAME_GROUP_TEST_1;
import static com.jmb.springfactory.model.factory.group.GroupSamples.NAME_GROUP_TEST_2;
import static com.jmb.springfactory.model.factory.group.GroupSamples.START_HOUR_GROUP_TEST_1;
import static com.jmb.springfactory.model.factory.group.GroupSamples.START_HOUR_GROUP_TEST_2;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jmb.springfactory.model.dto.WorkGroupDto;

public final class GroupDtoFactory {

  private GroupDtoFactory() {}

  public static WorkGroupDto createGroup(Integer id, String name, String startHour, String finishHour) {

    final WorkGroupDto group = new WorkGroupDto();
    group.setId(id);
    group.setName(name);
    group.setStartHour(startHour);
    group.setFinishHour(finishHour);

    return group;
  }

  public static WorkGroupDto createSampleDefaultGroupDto() {
    return createGroup(ID_GROUP_TEST_1, NAME_GROUP_TEST_1, START_HOUR_GROUP_TEST_1, FINISH_HOUR_GROUP_TEST_1);
  }

  public static Stream<WorkGroupDto> createStreamSampleDefaultGroup() {
    return Stream.of(createGroup(ID_GROUP_TEST_1, NAME_GROUP_TEST_1, START_HOUR_GROUP_TEST_1, FINISH_HOUR_GROUP_TEST_1),
        createGroup(ID_GROUP_TEST_2, NAME_GROUP_TEST_2, START_HOUR_GROUP_TEST_2, FINISH_HOUR_GROUP_TEST_2),
        createGroup(ID_GROUP_TEST_2, NAME_GROUP_TEST_2, START_HOUR_GROUP_TEST_2, FINISH_HOUR_GROUP_TEST_2));
  }

  public static List<WorkGroupDto> createListSampleDefaultGroup() {
    return createStreamSampleDefaultGroup().collect(Collectors.toList());
  }
}
