package com.jmb.springfactory.service.statistic;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.comment.CommentMySQLService;
import com.jmb.springfactory.dao.group.GroupMongoService;
import com.jmb.springfactory.dao.task.TaskMySQLService;
import com.jmb.springfactory.dao.worklog.WorkLogMySQLService;
import com.jmb.springfactory.model.bo.QueryTaskObject;
import com.jmb.springfactory.model.dto.StatisticDto;
import com.jmb.springfactory.model.entity.Task;
import com.jmb.springfactory.model.entity.WorkGroup;
import com.jmb.springfactory.model.entity.WorkLog;
import com.jmb.springfactory.service.BaseService;

@Service
public class StatisticServiceImpl extends BaseService implements StatisticService {

    @Autowired
    private TaskMySQLService taskMySQLService;

    @Autowired
    private WorkLogMySQLService workLogMySQLService;

    @Autowired
    private GroupMongoService groupMongoService;

    @Autowired
    private CommentMySQLService commentMySQLService;

    @Override
    public StatisticDto buildStatisticInformationByOrder(final Integer scheduleId, final Integer orderId) {

        serviceLog.info(String.format("Search task in schedule %s and order %s", scheduleId, orderId));
        final StatisticDto statistics = new StatisticDto();
        final QueryTaskObject queryTask = QueryTaskObject.builder().orderId(orderId).scheduleId(scheduleId).build();
        final List<Task> tasks = taskMySQLService.findAll(queryTask).collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(tasks)) {

            serviceLog.info("Extracting general statistic data");
            statistics.setPriority(extractPriorityData(tasks));
            statistics.setStatus(estractStatusData(tasks));
            statistics.setEstimatedHours(extractEstimatedHours(tasks));
            statistics.setWorkedHours(extractWorkedHours(tasks));

            serviceLog.info("Extracting group statistic data");
            statistics.setWorkedHoursByGroup(extractGroupTaskWorkedHours());
            statistics.setTaskByGroup(extractGroupTask());

            serviceLog.info("Extracting comment statistic data");
            statistics.setCommentByTask(extractTaskComment(tasks));

            serviceLog.info("Calculating total estimated and worked time");
            statistics.setTotalEstimatedTime(calculateTotalTime(statistics.getEstimatedHours()));
            statistics.setTotalWorkedHours(calculateTotalTime(statistics.getWorkedHours()));
        }

        return statistics;
    }

    private Long calculateTotalTime(Map<String, Long> estimatedHours) {
        return estimatedHours.entrySet().stream().map(Entry::getValue).reduce(Long::sum).orElse(0L);
    }

    private Map<String, Long> extractTaskComment(final List<Task> tasks) {

        final Function<Task, SimpleEntry<String, Long>> countCommentByTask = task -> {
            final Long numberOfComments = commentMySQLService.countByTask(task.getId());
            return new SimpleEntry<String, Long>(task.getName(), numberOfComments);
        };

        return tasks.stream().map(countCommentByTask)
                .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
    }

    private Map<String, Long> extractGroupTask() {

        return groupMongoService.findAll().map(group -> {
            final Long numberOfTasks = taskMySQLService.countByGroupId(group.getId());
            return new SimpleEntry<String, Long>(group.getName(), numberOfTasks);
        }).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
    }

    private Map<String, Long> extractGroupTaskWorkedHours() {
        return groupMongoService.findAll().map(extractHoursWorkedByGroup)
                .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
    }

    private final Function<WorkGroup, SimpleEntry<String, Long>> extractHoursWorkedByGroup = group -> {

        final QueryTaskObject queryTask = QueryTaskObject.builder().groupId(group.getId()).build();

        final List<WorkLog> workLogs = taskMySQLService.findAll(queryTask).map(Task::getId)
                .map(workLogMySQLService::findByTask).findFirst().orElse(Collections.emptyList());

        final Long hoursWorked = workLogs.stream().map(WorkLog::getHoursWorked).reduce(Double::sum).map(Math::round)
                .orElse(0L);

        return new SimpleEntry<String, Long>(group.getName(), hoursWorked);
    };

    private Map<String, Long> extractWorkedHours(List<Task> tasks) {

        final Map<String, Long> hoursWorked = new HashMap<>();

        for (Task task : tasks) {
            final Double hours = sumWorkedHoursInTask(task);
            hoursWorked.put(task.getName(), Math.round(hours));
        }

        return hoursWorked;
    }

    private Double sumWorkedHoursInTask(Task task) {
        final List<WorkLog> worklogs = workLogMySQLService.findByTask(task.getId());
        return worklogs.stream().map(WorkLog::getHoursWorked).reduce(Double::sum).orElse(0D);
    }

    private Map<String, Long> extractEstimatedHours(List<Task> tasks) {
        final Function<Task, Long> getEstimatedTime = task -> Math.round(task.getEstimatedTime());
        return tasks.stream().collect(Collectors.toMap(Task::getName, getEstimatedTime));
    }

    private Map<String, Long> estractStatusData(List<Task> tasks) {
        return countGroupingBy(Task::getStatus, tasks);
    }

    private Map<String, Long> extractPriorityData(List<Task> tasks) {
        return countGroupingBy(Task::getPriority, tasks);
    }

    private Map<String, Long> countGroupingBy(final Function<Task, Enum> groupFunction, final List<Task> tasks) {
        return tasks.stream().map(groupFunction).map(Enum::name)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

}
