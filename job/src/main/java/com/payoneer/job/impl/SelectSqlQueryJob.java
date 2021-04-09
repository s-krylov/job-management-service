package com.payoneer.job.impl;

import lombok.Cleanup;
import lombok.val;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelectSqlQueryJob extends SqlQueryJob {

    public SelectSqlQueryJob() {
        super(Action.SELECT, "songs");
    }

    @Override
    Object execute(Map<String, String> params, PreparedStatement ps) throws SQLException {
        int idx = 1;
        @Cleanup
        val rs = ps.executeQuery();
        val metaData = rs.getMetaData();
        val columnCount = metaData.getColumnCount();
        val res = new ArrayList<Map<String, String>>();
        while(rs.next()) {
            val map = new HashMap<String, String>();
            for (int i = 1; i <= columnCount; i++ ) {
                val columnName = metaData.getColumnLabel(i);
                val value = rs.getString(i);
                map.put(columnName, value);
            }
            res.add(map);
        }
        return res;
    }
}
