package com.payoneer.job.service.dto;

import com.payoneer.job.common.JobState;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class JobInfo {

    private Long id;
    private JobState state;

}
