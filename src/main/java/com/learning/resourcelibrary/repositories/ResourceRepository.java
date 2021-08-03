package com.learning.resourcelibrary.repositories;

import com.learning.resourcelibrary.models.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface ResourceRepository extends CrudRepository<Resource, Long> {
    List<Resource> findAll(Pageable pageable);
}
