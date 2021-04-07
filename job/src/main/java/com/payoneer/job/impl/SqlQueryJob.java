package com.payoneer.job.impl;

import com.payoneer.job.common.Job;
import com.payoneer.job.common.JobState;

import java.util.Map;

public class SqlQueryJob implements Job {


    @Override
    public JobState getState() {
        return null;
    }

    @Override
    public void setState(JobState state) {

    }

    @Override
    public Object run(Map params) {
        System.out.println("lol");
        return null;
    }

    @Override
    public void rollback() {

    }
}
