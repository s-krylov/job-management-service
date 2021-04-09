package com.payoneer.job.common;

public class JobRunException extends RuntimeException {

    public JobRunException(String message) {
        super(message);
    }

    public JobRunException(String message, Throwable cause) {
        super(message, cause);
    }


}
