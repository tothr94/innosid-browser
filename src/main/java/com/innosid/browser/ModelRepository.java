package com.innosid.browser;

import java.util.Optional;
import java.util.Set;

public interface ModelRepository {
    Set<String> getModelNames();

    Optional<byte[]> getModel(String name);
}
