package com.payoneer.job.service.service;



import com.payoneer.job.service.dto.JobInfo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Job management service
 */
public interface JobManagementService {

    /**
     * Execute job {@code name} with parameters {@code params}
     * @param name job name
     * @param params job parameters
     * @return execution result
     */
    Object executeJob(String name, Map<String, String> params);

    /**
     * Schedule job {@code name} with parameters {@code params} at {@code startAt}
     * @param name job name
     * @param params job parameters
     * @param startAt job start date and time
     * @return some result
     */
    Object scheduleJob(String name, Map<String, String> params, LocalDateTime startAt);

    /**
     * Get job info by {@code id}
     * @param id job identifier
     * @return job info
     */
    JobInfo getJobInfo(Long id);

    /**
     * Get all job info
     * @return list of all jobs
     */
    List<JobInfo> getAllJobInfo();
}
