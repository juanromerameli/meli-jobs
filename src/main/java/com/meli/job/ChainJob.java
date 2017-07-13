package com.meli.job;

import com.meli.utils.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * A Chain Job is a representation of a group of Jobs.
 * This group may be composed of different kinds of jobs.
 * <p>
 * The idea of the chain jobs is that this group is call
 * sequentially and the result of each jobs is the input data
 * for the next.
 *
 * @author Juan Manuel Romera Ferrio
 */
public class ChainJob extends Job {

    private List<Job> jobs;

    protected ChainJob(String name, List<Job> jobs) {
        super(name);
        this.jobs = jobs;
    }


    @Override
    protected void execute() {

        JobResults actualResult = null;

        for (Job job : jobs) {
            if (actualResult != null)
                job.setInputData(actualResult.getData().toArray());

            actualResult = job.call();
        }

        this.setJobResults(actualResult);
    }

    public static class Builder {
        private String name;
        private List<Job> jobs;


        public Builder() {
            this.jobs = new ArrayList<>();
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder job(Job job) {
            this.jobs.add(job);
            return this;
        }

        public ChainJob create() {

            Preconditions.checkNullOrEmpty(name, "Name can't be null or empty");
            Preconditions.checkNullOrEmpty(jobs, "Jobs can't be null or empty");

            return new ChainJob(this.name, this.jobs);
        }
    }
}
