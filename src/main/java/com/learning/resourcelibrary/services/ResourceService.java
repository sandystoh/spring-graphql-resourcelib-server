package com.learning.resourcelibrary.services;

import com.learning.resourcelibrary.input.ResourceInput;
import com.learning.resourcelibrary.models.Resource;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ResourceService {
    List<Resource> getResources();

    Optional<Resource> findById(long l);

    Resource addResource(ResourceInput input);

    Resource updateResource(ResourceInput input);

    Long deleteResource(Long id);

    Long markResourceCompleted(Long id, Boolean completed);
}
