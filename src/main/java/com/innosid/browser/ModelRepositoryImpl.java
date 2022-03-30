package com.innosid.browser;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class ModelRepositoryImpl implements ModelRepository {
    private final Environment environment;

    public ModelRepositoryImpl(Environment environment) {
        this.environment = environment;
    }

    private Stream<File> getFiles() {
        return Optional.ofNullable(environment.getProperty("model-path"))
                .map(File::new)
                .map(directory -> directory.listFiles(File::isFile))
                .stream()
                .flatMap(Arrays::stream);
    }

    @Override
    public Set<String> getModelNames() {
        return this.getFiles()
                .map(File::getName)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public Optional<byte[]> getModel(final String name) {
        return this.getFiles()
                .filter(file -> Objects.equals(file.getName(), name))
                .map(File::toPath)
                .map(path -> {
                    try {
                        return Files.readAllBytes(path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .findFirst();
    }
}
