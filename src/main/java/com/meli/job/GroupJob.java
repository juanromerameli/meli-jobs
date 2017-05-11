package com.meli.job;

import java.util.ArrayList;
import java.util.List;

/**
 * A Job that has a implementation that allow
 * shared the results of a list of jobs with an
 * {@link InputDataJob}
 *
 * @author Juan Manuel Romera Ferrio
 */
public class GroupJob extends Job {

    private List<Job> dataSourceJob;
    private InputDataJob inputDataJob;

    protected GroupJob(String name, List<Job> dataSourceJob, InputDataJob inputDataJob) {
        super(name);
        this.dataSourceJob = dataSourceJob;
        this.inputDataJob = inputDataJob;
    }

    @Override
    protected void execute() {
        List<Object> dataSource = new ArrayList<>();
        dataSourceJob.forEach(
                job -> {
                    dataSource.add(job.call().getData());
                }
        );

        inputDataJob.setInputData(dataSource.toArray());
        JobResults results = inputDataJob.call();
        setJobResults(results);
    }


    public static class Builder {
        private String name;
        private List<Job> dataSourceJob;
        private InputDataJob inputDataJob;

        public Builder() {
            this.dataSourceJob = new ArrayList<>();
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder dataSourceJob(Job dataSourceJob) {
            this.dataSourceJob.add(dataSourceJob);
            return this;
        }

        public Builder inputDataJob(InputDataJob inputDataJob) {
            this.inputDataJob = inputDataJob;
            return this;
        }

        public GroupJob create() {
            return new GroupJob(this.name, this.dataSourceJob, this.inputDataJob);
        }
    }
}
