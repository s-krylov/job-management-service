package com.payoneer.job.service.service;

import com.payoneer.job.service.dto.JobInfo;

import java.util.List;
import java.util.Map;

public interface JobManagementService {


    Map<String, String> executeJob(String name, String className, Map<String, String> params);

    JobInfo saveScheduledJob(String name);

    JobInfo findJobById(Long id);

    List<JobInfo> getAllJob();


}
