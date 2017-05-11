package com.meli.job;

import com.meli.utils.Preconditions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * An SaveFileJob is an implementation for a Job that
 * save the input data in a specified file
 *
 * @author Juan Manuel Romera Ferrio
 */
public class SaveFileJob extends InputDataJob {

    private SaveFileJob(String name, Path path) {
        super(name, getExecutable(path));
    }

    public SaveFileJob(String name, Path path, Object[] inputData) {
        super(name, getExecutable(path), inputData);
    }

    private static Executable getExecutable(Path path) {
        return items -> {
            try {
                Files.write(path, items.stream()
                        .map(object -> Objects.toString(object, null))
                        .collect(Collectors.toList()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return items;
        };
    }

    public static class Builder {

        private String name;
        private Object[] inputData;
        private Path path;

        public Builder() {
        }

        public SaveFileJob.Builder name(String name) {
            this.name = name;
            return this;
        }

        public SaveFileJob.Builder inputData(Object... inputData) {
            this.inputData = inputData;
            return this;
        }

        public SaveFileJob.Builder path(Path path) {
            this.path = path;
            return this;
        }

        public SaveFileJob create() {
            Preconditions.checkNullOrEmpty(name, "Name can't be null");
            Preconditions.checkNotNull(path, "Path can't be null");

            if (inputData == null)
                return new SaveFileJob(name, path);
            else
                return new SaveFileJob(name, path, inputData);
        }
    }
}
