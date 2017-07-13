package com.meli.job;

import com.meli.utils.Preconditions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * An SaveFileJob is an implementation for a Job that
 * save the input data in a specified file
 *
 * @author Juan Manuel Romera Ferrio
 */
public class SaveFileJob extends InputDataJob {

    private SaveFileJob(String name, Path path, Function<Object, String> mapper) {
        super(name, getExecutable(path, mapper));
    }

    public SaveFileJob(String name, Path path, Function<Object, String> mapper, Object[] inputData) {
        super(name, getExecutable(path, mapper), inputData);
    }

    private static Executable getExecutable(Path path, Function<Object, String> mapper) {
        return items -> {
            try {
                Files.write(path, items.stream()
                        .map(mapper)
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
        private Function<Object, String> mapper = object -> Objects.toString(object, null);

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

        public SaveFileJob.Builder mapper(Function<Object, String> mapper) {
            this.mapper = mapper;
            return this;
        }

        public SaveFileJob create() {
            Preconditions.checkNullOrEmpty(name, "Name can't be null");
            Preconditions.checkNotNull(path, "Path can't be null");

            SaveFileJob saveFileJob;
            if (inputData == null)
                saveFileJob = new SaveFileJob(name, path, mapper);
            else
                saveFileJob = new SaveFileJob(name, path, mapper, inputData);

            return saveFileJob;
        }
    }
}
