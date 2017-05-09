package com.meli.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Abstraction of Job. A Job represent a process than
 * can be executed.
 * A job has a name and an associated result.
 *
 * @author  Juan Manuel Romera Ferrrio
 */
public abstract class Job implements Callable<JobResults> {

    private Logger log = LoggerFactory.getLogger(Job.class);

    private String name;
    private JobResults jobResults;
    private Long runtTime;
    private List<Object> inputData;
    private boolean isDone;

    protected Job(String name) {
        this.name = name;
        this.jobResults = new JobResults();
        this.runtTime = 0L;
        this.inputData = Collections.emptyList();
        this.isDone = false;
    }

    protected abstract void execute();

    @Override
    public JobResults call() {
        long startTime = System.currentTimeMillis();

        this.log.info(this.getClass().getSimpleName() + " executing job " + this.name);

        this.execute();

        this.runtTime = System.currentTimeMillis() - startTime;
        this.isDone = true;

        return this.jobResults;
    }

    @Override
    public String toString() {
        return "Job{" +
                "name='" + name + '\'' +
                '}';
    }

    public void setInputData(Object... inputData) {
        this.inputData = Arrays.asList(inputData);
    }

    public List<Object> getInputData() {
        return this.inputData;
    }

    public String getName() {
        return name;
    }

    public Long getRuntTime() {
        return this.runtTime;
    }

    public void setJobResults(JobResults jobResults) {
        this.jobResults = jobResults;
    }

    public boolean isDone() {
        return isDone;
    }
}

