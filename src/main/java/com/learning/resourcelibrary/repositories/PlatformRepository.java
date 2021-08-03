package com.learning.resourcelibrary.repositories;

import com.learning.resourcelibrary.models.Platform;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlatformRepository extends CrudRepository<Platform, Long> {
    Optional<Platform> findByName(String name);
    Boolean existsByName(String name);
}
