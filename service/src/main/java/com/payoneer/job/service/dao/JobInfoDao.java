package com.payoneer.job.service.dao;

import com.payoneer.job.service.dto.JobInfo;

import java.util.List;
import java.util.Optional;


/**
 * JobInfo Dao
 */
public interface JobInfoDao {

    void save(JobInfo info);

    void update(JobInfo info);

    Optional<JobInfo> findById(Long id);

    List<JobInfo> getAll();
}
