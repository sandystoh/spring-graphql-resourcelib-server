package com.learning.resourcelibrary.services;

import com.learning.resourcelibrary.models.Author;
import com.learning.resourcelibrary.models.Resource;

import java.util.Optional;
import java.util.Set;

public interface AuthorService {
    Author addAuthor(String name);
}
