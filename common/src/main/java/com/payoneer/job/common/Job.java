package com.payoneer.job.common;

import java.util.Map;

/**
 * General purpose Job
 */
public interface Job {

    /**
     * Runs job with passed {@code params} as argument
     * @param params parameters
     * @return result
     */
    Object run(Map<String, String> params);

    /**
     * Action need to be done to commit changes. Job Specific
     */
    void commit();

    /**
     * Action need to be done to rollback changes. Job Specific
     */
    void rollback();

}
