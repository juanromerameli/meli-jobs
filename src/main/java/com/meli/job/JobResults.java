package com.meli.job;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Juan Manuel Romera Ferrrio
 */
public class JobResults {

    private List<Object> data;

    public JobResults() {
        this.data = new ArrayList<>();
    }

    public JobResults(List<Object> data) {
        this.data = data;
    }

    public List<Object> getData() {
        return this.data;
    }

    public void add(JobResults jobResults) {
        this.data.addAll(jobResults.data);
    }

}
