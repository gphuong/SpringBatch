package com.phuongheh.spring.batch.demo6.job;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

public class JobSkipPolicy implements SkipPolicy {
    @Override
    public boolean shouldSkip(Throwable throwable, int failedCount) throws SkipLimitExceededException {
        return (failedCount >= 1) ? false : true;
    }
}
