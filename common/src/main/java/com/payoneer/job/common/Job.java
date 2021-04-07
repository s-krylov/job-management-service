package com.payoneer.job.common;

import java.util.Map;

/**
 * General purpose Job
 */
public interface Job<T> {

    /**
     * @return current job state
     */
    JobState getState();

    /**
     * Sets current job state
     * @param state
     */
    void setState(JobState state);

    /**
     * Runs job with passed {@code params} as argument
     * @param params parameters
     * @return result
     */
    T run(Map<String, Object> params);


    /**
     * Action need to be done to rollback changes. Job Specific
     */
    void rollback();

}
