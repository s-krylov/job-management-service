package com.payoneer.job.service.quartzjob;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Map;

@Slf4j
public class JobWrapper implements Job {


    @Autowired
    private ApplicationContext applicationContext;

    @Override
    @SneakyThrows
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.debug("Start job execution");
        val key = context.getJobDetail().getKey();
        val name = key.getName();
        val group = key.getGroup();

        log.debug("Start job {} @ {} execution", name, group);


        val job = Class.forName("com.payoneer.job.impl.SqlQueryJob");
        val jobInstance = job.getConstructor().newInstance();
        job.getMethod("run", Map.class).invoke(jobInstance, context.getJobDetail().getJobDataMap());
//        jobInstance.run(context.getJobDetail().getJobDataMap());
        log.debug("Passed arguments : {}", context.getJobDetail().getJobDataMap());


        log.debug("Job {} @ {} execution finished", name, group);
    }
}
