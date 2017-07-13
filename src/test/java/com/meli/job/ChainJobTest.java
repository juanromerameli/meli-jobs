package com.meli.job;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by jromera on 12/5/17.
 */
public class ChainJobTest {

    @Test
    public void createJob() {
        InputDataJob testJob = new InputDataJob.Builder().name("input").executable(inputData -> inputData).create();
        ChainJob chainJob = new ChainJob.Builder().name("test").job(testJob).create();
        assertNotNull(chainJob);
    }

    @Test
    public void createJobWithErrors() {
        InputDataJob testJob = new InputDataJob.Builder().name("input").executable(inputData -> inputData).create();
        ChainJob chainJob = null;
        try {
            chainJob = new ChainJob.Builder().job(testJob).create();
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Name can't be null or empty");
        }

        assertNull(chainJob);

        try {
            chainJob = new ChainJob.Builder().name("test").create();
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Jobs can't be null or empty");
        }

        assertNull(chainJob);
    }

    @Test
    public void executeJob() {
        InputDataJob first = new InputDataJob.Builder().name("first").executable(inputData -> Arrays.asList(new Integer(10))).create();
        InputDataJob second = new InputDataJob.Builder().name("second").executable(inputData -> Arrays.asList(((Integer) inputData.get(0)) * 10)).create();

        ChainJob chainJob = new ChainJob.Builder().name("test").job(first).job(second).create();
        List<Object> result = chainJob.call().getData();

        assertEquals(result.size(), 1);
        assertEquals(result.get(0), 100);
        assertTrue(first.isDone());
        assertTrue(second.isDone());
        assertTrue(chainJob.isDone());
    }

    @Test
    public void executeJobWithErrors() {
        InputDataJob first = new InputDataJob.Builder().name("first").executable(inputData -> Arrays.asList(inputData.get(0))).create();
        InputDataJob second = mock(InputDataJob.class);

        ChainJob chainJob = new ChainJob.Builder().name("test").job(first).job(second).create();

        boolean error = false;
        JobResults result = null;
        try {
            result = chainJob.call();
        } catch (Exception e) {
            error = true;
        }

        assertTrue(error);
        assertNull(result);

        verify(second, never()).execute();
    }
}
