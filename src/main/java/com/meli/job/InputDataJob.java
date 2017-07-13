package com.meli.job;

import com.meli.utils.Preconditions;

import java.util.List;

/**
 * A Job implementation that allow set an
 * input data and a implementation to perform.
 *
 * @author Juan Manuel Romera Ferrio
 */
public class InputDataJob extends Job {

    private Executable executable;

    protected InputDataJob(String name, Executable executable) {
        super(name);
        this.executable = executable;
    }

    protected InputDataJob(String name, Executable executable, Object... inputData) {
        super(name);
        this.setInputData(inputData);
        this.executable = executable;
    }

    @Override
    protected void execute() {
        if (executable != null) {
            List<Object> result = this.executable.execute(this.getInputData());
            setJobResults(new JobResults(result));
        }
    }

    public static class Builder {

        private String name;
        private Object[] inputData;
        private Executable executable;

        public Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder inputData(Object... inputData) {
            this.inputData = inputData;
            return this;
        }

        public Builder executable(Executable executable) {
            this.executable = executable;
            return this;
        }

        public InputDataJob create() {

            Preconditions.checkNullOrEmpty(name, "Name can't be null or empty");
            Preconditions.checkNotNull(executable, "Executable can't be null");

            if (inputData == null)
                return new InputDataJob(name, executable);
            else
                return new InputDataJob(name, executable, inputData);
        }
    }
}
