package com.meli.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Job that has a implementation that allow
 * link the result to a father job with child jobs
 * that use this results as input data.
 *
 * @author Juan Manuel Romera Ferrio
 */
public class FamilyJob extends Job {

    private Job fatherJob;
    private Map<String, Job> childJobs;

    private FamilyJob(String name, Job fatherJob, Map<String, Job> childJobs) {
        super(name);
        this.fatherJob = fatherJob;
        this.childJobs = childJobs;
    }

    @Override
    protected void execute() {
        JobResults result = new JobResults();
        JobResults fatherResult = fatherJob.call();
        List<Object> objects = fatherResult.getData();

        for (Object object : objects) {
            for (Job childJob : childJobs.values()) {
                childJob.setInputData(object);
                JobResults childResult = childJob.call();
                result.add(childResult);
            }
        }

        this.setJobResults(result);
    }


    public static class Builder {
        private String name;
        private Job fatherJob;
        private Map<String, Job> childJobs;

        public Builder() {
            this.childJobs = new HashMap<>();
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder father(Job fatherJob) {
            this.fatherJob = fatherJob;
            return this;
        }

        public Builder child(Job childJob) {
            this.childJobs.put(childJob.getName(), childJob);
            return this;
        }

        public FamilyJob create() {
            return new FamilyJob(this.name, this.fatherJob, this.childJobs);
        }
    }
}
