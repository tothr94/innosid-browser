package com.innosid.browser;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping
public class ModelControllerImpl implements ModelController {
    private final ModelRepository repository;

    public ModelControllerImpl(ModelRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Override
    public Set<String> getModelNames() {
        return repository.getModelNames();
    }

    @GetMapping("/{name}")
    @Override
    public ResponseEntity<byte[]> getModel(@PathVariable final String name) {
        return repository.getModel(name)
                .map(bytes -> ResponseEntity
                        .ok()
                        .header("Content-Disposition", "attachment; filename=\"" + name + "\"")
                        .header("Content-Type", "model/gltf-binary")
                        .body(bytes))
                .orElseGet(() -> ResponseEntity
                        .status(404)
                        .body(null));
    }
}
