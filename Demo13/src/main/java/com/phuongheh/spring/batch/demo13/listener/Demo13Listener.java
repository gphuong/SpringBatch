package com.phuongheh.spring.batch.demo13.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

public class Demo13Listener extends JobExecutionListenerSupport {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Before " + jobExecution.getJobInstance().getJobName() + " execution");
        jobExecution.getExecutionContext().putString("beforeJob", "beforeValue");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("After demo13 execution. The value of beforeJob key is=" + jobExecution.getExecutionContext().getString("beforeJob"));
        if (jobExecution.getStatus().equals(BatchStatus.COMPLETED)) {
            System.out.println("Success!");
        } else {
            System.out.println("Failed!");
        }
    }

}
