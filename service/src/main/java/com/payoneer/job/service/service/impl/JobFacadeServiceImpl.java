package com.payoneer.job.service.service.impl;

import com.payoneer.job.common.JobState;
import com.payoneer.job.service.dto.JobInfo;
import com.payoneer.job.service.quartzjob.JobWrapper;
import com.payoneer.job.service.service.JobFacadeService;
import com.payoneer.job.service.service.JobManagementService;
import com.payoneer.job.service.utils.JobConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class JobFacadeServiceImpl implements JobFacadeService {


    private final Scheduler scheduler;
    private final JobManagementService jobManagementService;

    @Override
    public Object executeJob(String className, Map<String, String> params) {
        val jobName = createJobName(className);
        return jobManagementService.executeJob(jobName, className, params);
    }

    @Override
    public JobInfo scheduleJob(String className, Map<String, String> params, LocalDateTime startAt) {
        val jobName = createJobName(className);
        try {
            // todo priority

            scheduler.scheduleJob(
                    JobBuilder.newJob(JobWrapper.class)
                            .storeDurably(true)
                            .withIdentity(jobName, className)
                            .setJobData(new JobDataMap(params))
                            .usingJobData(JobConstants.CLASS_NAME_KEY, className)
                            .build(),
                    TriggerBuilder.newTrigger()
                            .startAt(Date.from(startAt.atZone(ZoneId.systemDefault()).toInstant()))
                            .build());
            return jobManagementService.saveScheduledJob(jobName);
        } catch (Exception e) {
            log.error("Error occurred during job scheduling", e);
            return JobInfo.builder()
                    .name(jobName)
                    .state(JobState.FAILED)
                    .build();
        }
    }

    @Override
    public JobInfo getJobInfo(Long id) {
        return jobManagementService.findJobById(id);
    }

    @Override
    public List<JobInfo> getAllJobInfo() {
        return jobManagementService.getAllJob();
    }


    /**
     * Create Job name using passed params
     * @param className class name
     * @return job name
     */
    private String createJobName(String className) {
        return MessageFormat.format("{0}.{1}", className, UUID.randomUUID().toString().replaceAll("-", ""));

    }
}
