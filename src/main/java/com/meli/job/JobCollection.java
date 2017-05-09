package com.meli.job;

import java.util.*;

/**
 * A {@link Map} that stores {@link Job}
 *
 * @author Juan Manuel Romera Ferrrio
 */
public class JobCollection implements Map<String, Job> {

    private Map<String, Job> jobs;

    public JobCollection() {
        this.jobs = new HashMap<>();
    }

    @Override
    public int size() {
        return this.jobs.size();
    }

    @Override
    public boolean isEmpty() {
        return this.jobs.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return jobs.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return jobs.containsValue(value);
    }

    @Override
    public Job get(Object key) {
        return jobs.get(key);
    }

    @Override
    public Job put(String key, Job value) {
        return jobs.put(key, value);
    }

    @Override
    public Job remove(Object key) {
        return jobs.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends Job> m) {
        jobs.putAll(m);
    }

    @Override
    public void clear() {
        this.jobs.clear();
    }

    @Override
    public Set<String> keySet() {
        return jobs.keySet();
    }

    @Override
    public Collection<Job> values() {
        return jobs.values();
    }

    @Override
    public Set<Entry<String, Job>> entrySet() {
        return jobs.entrySet();
    }

    public Job add(Job job) {
        return put(job.getName(), job);
    }
}
