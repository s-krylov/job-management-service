package com.payoneer.job.service.quartzjob;

import com.payoneer.job.service.service.JobManagementService;
import com.payoneer.job.service.utils.JobConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class JobWrapper implements Job {


    private final JobManagementService jobManagementService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        val key = context.getJobDetail().getKey();
        val params = context.getJobDetail().getJobDataMap()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> String.valueOf(e.getValue())));
        val name = key.getName();
        val className = Optional.ofNullable(params.remove(JobConstants.CLASS_NAME_KEY)).orElse(name);

        try {

            val result = jobManagementService.executeJob(name, className, params);
            context.setResult(result);

        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }
}
