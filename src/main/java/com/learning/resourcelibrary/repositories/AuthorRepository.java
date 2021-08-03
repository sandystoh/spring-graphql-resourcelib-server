package com.learning.resourcelibrary.repositories;

import com.learning.resourcelibrary.models.Author;
import com.learning.resourcelibrary.models.Platform;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthorRepository  extends CrudRepository<Author, Long> {
    Optional<Author> findByName(String name);
}
