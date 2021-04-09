package com.payoneer.job.service.dao.impl;

import com.payoneer.job.common.JobState;
import com.payoneer.job.service.dao.JobInfoDao;
import com.payoneer.job.service.dto.JobInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
@Slf4j
public class JobInfoDaoImpl implements JobInfoDao {

    private final JdbcTemplate jdbcTemplate;

    private static final String SAVE_QUERY = "" +
            "INSERT INTO job_info (name, status) VALUES (?, ?)" +
            "ON CONFLICT ON CONSTRAINT name_unique " +
            "DO UPDATE SET status = EXCLUDED.status";
    private static final String UPDATE_QUERY = "" +
            "UPDATE job_info SET name = ?, status = ?" +
            "WHERE id = ?";
    private static final String GET_ALL_QUERY = "SELECT id, name, status FROM job_info";
    private static final String FIND_BY_ID_QUERY = GET_ALL_QUERY + " WHERE id = ?";

    @Override
    public void save(JobInfo info) {
        log.trace("Start saving job info. {}", info);
        val keyHolder = new GeneratedKeyHolder();
        val statement = new PreparedStatementCreatorFactory(SAVE_QUERY, Types.VARCHAR, Types.VARCHAR);

        statement.setReturnGeneratedKeys(true);
        statement.setGeneratedKeysColumnNames("id");

        jdbcTemplate.update(statement.newPreparedStatementCreator(new Object[] {info.getName(),
                        info.getState().name()}), keyHolder);

        val id = keyHolder.getKey().longValue();
        info.setId(id);
        log.trace("Job info successfully saved. id : {}", id);

    }

    @Override
    public void update(JobInfo info) {
        log.trace("Start updating job info. {}", info);
        jdbcTemplate.update(UPDATE_QUERY, info.getName(), info.getState().name(), info.getId());
        log.trace("Job info successfully updated");
    }


    @Override
    public Optional<JobInfo> findById(Long id) {
        log.trace("Start query job info by id {}", id);
        val res = jdbcTemplate.query(FIND_BY_ID_QUERY, this::mapJobInfo, id).stream().findFirst();
        log.trace("Job info query by id successfully finished");
        return res;
    }

    @Override
    public List<JobInfo> getAll() {
        log.trace("Start query all job info");
        val res = jdbcTemplate.query(GET_ALL_QUERY, this::mapJobInfo);
        log.trace("Query all job info successfully finished");
        return res;
    }

    private JobInfo mapJobInfo(ResultSet rs, int row) throws SQLException {
        int idx = 1;
        return JobInfo.builder()
                .id(rs.getLong(idx++))
                .name(rs.getString(idx++))
                .state(JobState.valueOf(rs.getString(idx)))
                .build();
    }
}
