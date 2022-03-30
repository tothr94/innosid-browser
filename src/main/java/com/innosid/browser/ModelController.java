package com.innosid.browser;

import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface ModelController {
    Set<String> getModelNames();

    ResponseEntity<byte[]> getModel(String name);
}
