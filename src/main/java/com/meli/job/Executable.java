package com.meli.job;

import java.util.List;

/**
 * Created by jromera on 8/5/17.
 */
@FunctionalInterface
public interface Executable {

    public abstract List<Object> execute(List<Object> inputData);
}
