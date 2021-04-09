package com.payoneer.job.impl;

import com.payoneer.job.common.Job;
import com.payoneer.job.common.JobRunException;
import com.payoneer.job.common.JobUtils;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Map;


@Slf4j
public abstract class SqlQueryJob implements Job {

    private final String query;

    public SqlQueryJob(Action action, String table, Object ... params) {
        Object[] merged = new Object[params.length + 1];
        System.arraycopy(params, 0, merged, 1, params.length);
        merged[0] = table;
        this.query =  action.generateSql(merged);
    }

    private Connection connection;

    @Override
    public Object run(Map<String, String> params) {
        val jdbcUrl = JobUtils.getParamOrFail(params,"jdbcUrl");
        val user = JobUtils.getParamOrFail(params,"user");
        val password = JobUtils.getParamOrFail(params,"password");

        try {
            connection = DriverManager.getConnection(jdbcUrl, user, password);
            connection.setAutoCommit(false);
            @Cleanup
            val ps = connection.prepareStatement(query);

            return execute(params, ps);
        } catch (SQLException e) {
            log.error("Sql error", e);
            throw new JobRunException("Sql error", e);
        }

    }

    abstract Object execute(Map<String, String> params, PreparedStatement ps) throws SQLException;

    @Override
    public void commit() {
        try {
            if (connection != null) {
                connection.commit();
            }
        } catch (SQLException sqle) {
            throw new JobRunException("Error occurred during commit", sqle);
        }
    }

    @Override
    public void rollback() {
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException sqle) {
            throw new JobRunException("Error occurred during rollback", sqle);
        }
    }


    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    enum Action {


        SELECT("SELECT * FROM {0}"),
        UPDATE("UPDATE {0} SET {1} = ? WHERE {2} = ?");

        private final String queryTemplate;


        public String generateSql(Object ... params) {
            return MessageFormat.format(queryTemplate, params);
        }
    }
}
