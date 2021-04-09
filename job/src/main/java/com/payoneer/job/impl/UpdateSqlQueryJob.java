package com.payoneer.job.impl;

import com.payoneer.job.common.JobUtils;
import lombok.val;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

public class UpdateSqlQueryJob extends SqlQueryJob {

    public UpdateSqlQueryJob() {
        super(Action.UPDATE, "songs", "name", "id");
    }

    @Override
    Object execute(Map<String, String> params, PreparedStatement ps) throws SQLException {
        val value = JobUtils.getParamOrFail(params,"newValue");
        val id = Long.parseLong(JobUtils.getParamOrFail(params,"id"));
        int idx = 1;
        ps.setString(idx++, value);
        ps.setLong(idx, id);
        val updated = ps.executeUpdate();
        return Collections.singletonMap("updated", updated);
    }
}
