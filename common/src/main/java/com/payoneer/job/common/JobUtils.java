package com.payoneer.job.common;

import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Optional;

@UtilityClass
public class JobUtils {

    public static Optional<String> getParam(Map<String, String> param, String name) {
        return Optional.ofNullable(param.get(name));
    }

    public static String getParamOrFail(Map<String, String> param, String name) {
        return getParam(param, name).orElseThrow(() -> new JobRunException(name + " not passed"));

    }
}
