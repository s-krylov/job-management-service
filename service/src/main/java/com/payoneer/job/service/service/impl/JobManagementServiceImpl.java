package com.payoneer.job.service.service.impl;

import com.payoneer.job.common.JobState;
import com.payoneer.job.service.dto.JobInfo;
import com.payoneer.job.service.quartzjob.JobWrapper;
import com.payoneer.job.service.service.JobManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class JobManagementServiceImpl implements JobManagementService {


    private final Scheduler scheduler;

    @Override
    public Object executeJob(String name, Map<String, String> params) {
        return null;
    }

    @Override
    public Object scheduleJob(String name, Map<String, String> params, LocalDateTime startAt) {
        try {
            scheduler.scheduleJob(
                    JobBuilder.newJob(JobWrapper.class)
                            .storeDurably(true)
                            .withIdentity(startAt.toString(), name)
                            .setJobData(new JobDataMap(params))
                            .build(),
                    TriggerBuilder.newTrigger()
                            .startAt(Date.from(startAt.atZone(ZoneId.systemDefault()).toInstant()))
                            .build());
            return "TODO FIXME";
        } catch (Exception e) {
            log.error("Error occurred during job scheduling");
            return JobState.FAILED;
        }
    }

    @Override
    public JobInfo getJobInfo(Long id) {
        // todo fixme replace mock with data query
        return JobInfo.builder()
                .id(id)
                .state(JobState.QUEUED)
                .build();
    }

    @Override
    public List<JobInfo> getAllJobInfo() {
        return Collections.emptyList();
    }
}
