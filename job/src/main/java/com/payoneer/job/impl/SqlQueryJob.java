package com.payoneer.job.impl;

import com.payoneer.job.common.Job;

import java.util.Map;


public class SqlQueryJob implements Job {



    @Override
    public Map<String, String> run(Map<String, String> params) {
        System.out.println("run");
        return null;
    }

    @Override
    public void commit() {
        System.out.println("commit");
    }

    @Override
    public void rollback() {
        System.out.println("rollback");
    }
}
