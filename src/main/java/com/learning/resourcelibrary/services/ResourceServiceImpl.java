package com.learning.resourcelibrary.services;

import com.learning.resourcelibrary.exception.ResourceNotFoundException;
import com.learning.resourcelibrary.input.ResourceInput;
import com.learning.resourcelibrary.models.Author;
import com.learning.resourcelibrary.models.Platform;
import com.learning.resourcelibrary.models.Resource;
import com.learning.resourcelibrary.models.Topic;
import com.learning.resourcelibrary.repositories.PlatformRepository;
import com.learning.resourcelibrary.repositories.ResourceRepository;
import com.learning.resourcelibrary.repositories.TopicRepository;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepository resourceRepository;
    private final AuthorService authorService;
    private final PlatformRepository platformRepository;
    private final TopicRepository topicRepository;

    public ResourceServiceImpl(ResourceRepository resourceRepository, AuthorService authorService, PlatformRepository platformRepository, TopicRepository topicRepository) {
        this.resourceRepository = resourceRepository;
        this.authorService = authorService;
        this.platformRepository = platformRepository;
        this.topicRepository = topicRepository;
    }

    @Override
    public List<Resource> getResources() {
        int page = 0, size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by("updatedDate").descending());
        List<Resource> resources = new ArrayList<>();
        resourceRepository.findAll(pageable).iterator().forEachRemaining(resources::add);
        System.out.println(resources);
        return resources;
    }

    @Override
    public Optional<Resource> findById(long l) {
        return resourceRepository.findById(l);
        // .orElseThrow(() -> new RuntimeException("Resource Not Found!"));
    }

    @Override
    public Resource addResource(ResourceInput input) {
        Author author = authorService.addAuthor(input.getAuthorName());
        Platform platform = platformRepository.findById(input.getPlatformId()).orElse(null);

        Resource resource = new Resource();
        populateResource(input, resource, author, platform);
        return resource;
    }

    @Override
    public Resource updateResource(ResourceInput input) {

        Resource resource = resourceRepository.findById(input.getId()).orElse(null);

        if (resource != null) {
            Author author = authorService.addAuthor(input.getAuthorName());
            Platform platform = platformRepository.findById(input.getPlatformId()).orElse(null);

            populateResource(input, resource, author, platform);
        }
        return resource;
    }

    private void populateResource(ResourceInput input, Resource resource, Author author, Platform platform) {
        resource.setTitle(input.getTitle());
        resource.setDescription(input.getDescription());
        resource.setAuthor(author);
        resource.setPlatform(platform);
        resource.setPublicationYear(input.getPublicationYear());
        resource.setType(input.getType());
        resource.setUrl(input.getUrl());
        resource.setLength(input.getLength());
        resource.setRating(input.getRating());
        resource.setLevel(input.getLevel());
        resource.setCompleted(input.getCompleted());

        Set<Topic> newTopics = new HashSet<Topic>();
        input.getTopics().forEach(topicId -> {
            Topic topic = topicRepository.findById(topicId).orElse(null);
            if (topic != null) {
                newTopics.add(topic);
            }
        });
        resource.setTopics(newTopics);
        resourceRepository.save(resource);
    }

    @Override
    public Long deleteResource(Long id) {
        resourceRepository.deleteById(id);
        return id;
    }

    @Override
    public Long markResourceCompleted(Long id, Boolean completed) {
        Resource existingResource = resourceRepository.findById(id).orElse(null);
        if (existingResource != null) {
            existingResource.setCompleted(completed);
            resourceRepository.save(existingResource);
            return id;
        } else {
            throw new ResourceNotFoundException("Resource ID: " + id + " not found");
        }
    }
}
