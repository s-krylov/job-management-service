package com.payoneer.job.service.dto;

import com.payoneer.job.common.JobState;
import lombok.Builder;
import lombok.Data;


@Data
@Builder(toBuilder = true)
public class JobInfo {

    private Long id;
    private String name;
    private JobState state;

}
