package com.meli.job;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jromera on 9/5/17.
 */
public class InputDataJobTest {


    @Test
    public void createJob() {
        InputDataJob inputDataJob = new InputDataJob.Builder().name("test").executable(inputData -> inputData).create();
        assertNotNull(inputDataJob);
    }

    @Test
    public void createJobWithErrors() {

        InputDataJob inputDataJob = null;
        try {
            inputDataJob = new InputDataJob.Builder().executable(inputData -> inputData).create();
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Name can't be null or empty");
        }

        assertNull(inputDataJob);

        try {
            inputDataJob = new InputDataJob.Builder().name("test").create();
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Executable can't be null");
        }

        assertNull(inputDataJob);
    }

    @Test
    public void executeJob() {
        InputDataJob inputDataJob = new InputDataJob.Builder().name("test").executable(inputData -> {
            Integer item = (Integer) inputData.get(0);
            item++;
            return Arrays.asList(item);
        }).inputData(new Integer(0)).create();

        List<Object> result = inputDataJob.call().getData();

        assertEquals(result.size(), 1);
        assertEquals(result.get(0), 1);
        assertTrue(inputDataJob.isDone());
    }

    @Test
    public void executeJobWithErrors() {
        InputDataJob inputDataJob = new InputDataJob.Builder().name("test").executable(inputData -> {
            inputData.get(0);
            return inputData;
        }).create();

        boolean error = false;
        JobResults result = null;

        try {
            result = inputDataJob.call();
        } catch (Exception e) {
            error = true;
        }

        assertTrue(error);
        assertNull(result);
    }


}
