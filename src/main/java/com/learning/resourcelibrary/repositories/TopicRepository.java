package com.learning.resourcelibrary.repositories;

import com.learning.resourcelibrary.models.Platform;
import com.learning.resourcelibrary.models.Topic;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TopicRepository  extends CrudRepository<Topic, Long> {
    Optional<Topic> findByName(String name);
    Boolean existsByName(String name);
}
