package com.meli.job;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * A Service that provide an Executor to invoke a
 * {@link Job} or a {@link JobCollection}
 *
 * @author Juan Manuel Romera Ferrio
 */
public class ExecutorJobService {

    private ExecutorService executor;

    public ExecutorJobService(ExecutorService executor) {
        this.executor = executor;
    }

    public List<Future<JobResults>> execute(JobCollection jobCollection) throws InterruptedException {
        return this.executor.invokeAll(jobCollection.values());
    }

    public Future<JobResults> execute(Job job) {
        return this.executor.submit(job);
    }

}
