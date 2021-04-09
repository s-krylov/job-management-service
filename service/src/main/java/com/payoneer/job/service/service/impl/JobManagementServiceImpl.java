package com.payoneer.job.service.service.impl;

import com.payoneer.job.common.JobState;
import com.payoneer.job.service.dao.JobInfoDao;
import com.payoneer.job.service.dto.JobInfo;
import com.payoneer.job.service.service.JobManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class JobManagementServiceImpl implements JobManagementService {

    private final JobInfoDao jobInfoDao;

    @Override
    public Map<String, String> executeJob(String name, String className, Map<String, String> params) {

        log.debug("Start job {} execution", name);
        val jobInfo = JobInfo.builder()
                .name(name)
                .state(JobState.RUNNING)
                .build();
        Object instance = null;
        Class clazz = null;
        try {
            clazz = Class.forName(className);
//            val job = Class.forName("com.payoneer.job.impl.SqlQueryJob");
            instance = clazz.getConstructor().newInstance();

            jobInfoDao.save(jobInfo);

            val result = (Map<String, String>) clazz.getMethod("run", Map.class).invoke(instance, params);

            callSilently(clazz, "commit", instance);
            jobInfoDao.update(jobInfo.toBuilder()
                    .state(JobState.SUCCESS)
                    .build());
            log.debug("Job {} execution finished successfully", name);
            return result;
        } catch (Exception e) {
            log.error("Error occurred during job {} execution", name, e);
            callSilently(clazz, "rollback", instance);
            jobInfoDao.update(jobInfo.toBuilder()
                    .state(JobState.FAILED)
                    .build());
            log.debug("Job {} execution failed", name);
            return Collections.emptyMap();
        }

    }

    @Override
    public JobInfo saveScheduledJob(String name) {
        val jobInfo = JobInfo.builder()
                .name(name)
                .state(JobState.QUEUED)
                .build();
        jobInfoDao.save(jobInfo);
        return jobInfo;
    }


    @Override
    public JobInfo findJobById(Long id) {
        return jobInfoDao.findById(id).orElse(null);
    }

    @Override
    public List<JobInfo> getAllJob() {
        return jobInfoDao.getAll();
    }

    private void callSilently(Class clazz, String method, Object instance) {
        if (clazz == null || instance == null) {
            return;
        }
        try {
            clazz.getMethod(method).invoke(instance);
        } catch (Exception e) {
            log.error("Error occurred during silent action", e);
        }
    }
}
