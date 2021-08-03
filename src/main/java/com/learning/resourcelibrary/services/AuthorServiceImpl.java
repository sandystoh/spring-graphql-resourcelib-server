package com.learning.resourcelibrary.services;

import com.learning.resourcelibrary.models.Author;
import com.learning.resourcelibrary.repositories.AuthorRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author addAuthor(String name) {
        if (StringUtils.isNotBlank(name)) {
            Author author = authorRepository.findByName(name).orElse(null);
            if (author != null) {
                return author;
            } else {
                Author newAuthor = new Author(name);
                authorRepository.save(newAuthor);
                return newAuthor;
            }
        } else {
            return null;
        }
    }
}
