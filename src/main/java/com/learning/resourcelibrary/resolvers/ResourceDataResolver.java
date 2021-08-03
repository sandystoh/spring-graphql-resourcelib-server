package com.learning.resourcelibrary.resolvers;

import com.learning.resourcelibrary.exception.ResourceNotFoundException;
import com.learning.resourcelibrary.filters.ResourceFilter;
import com.learning.resourcelibrary.input.ResourceInput;
import com.learning.resourcelibrary.models.Resource;
import com.learning.resourcelibrary.models.Topic;
import com.learning.resourcelibrary.services.ResourceServiceImpl;
import com.netflix.graphql.dgs.*;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@DgsComponent
public class ResourceDataResolver {
    private final ResourceServiceImpl resourceService;

    public ResourceDataResolver(ResourceServiceImpl resourceService) {
        this.resourceService = resourceService;
    }

    @DgsQuery
    public List<Resource> allResources() {
        return resourceService.getResources();
    }

    @DgsQuery
    public Resource resource(String id) {
        Long idLong = Long.valueOf(id);
        Optional<Resource> foundResource = resourceService.findById(idLong);
        System.out.println(foundResource);
        if (foundResource.isEmpty()) {
            // DgsEntityNotFoundException
            throw new ResourceNotFoundException("No Resource by this ID exists: " + id);
        }
        return foundResource.get();
    }

    @DgsData(parentType = "Query", field = "resources")
    public List<Resource> getResources(@InputArgument(name = "filter", collectionType = ResourceFilter.class) Optional<ResourceFilter> filter) {
        List<Resource> resources = resourceService.getResources();
        if (filter.isEmpty()) {
            return resources;
        }
        return resources.stream().filter(resource -> this.matchFilter(filter.get(), resource)).collect(Collectors.toList());
    }

    private boolean matchFilter(ResourceFilter filter, Resource resource) {
        if (StringUtils.isNotBlank(filter.getType()) && !StringUtils.equals(resource.getType().toString(),
                StringUtils.defaultIfBlank(filter.getType(), StringUtils.EMPTY))) {
            return false;
        }
        if (StringUtils.isNotBlank(filter.getPlatformId()) && !StringUtils.equals(resource.getPlatform().getId().toString(),
                StringUtils.defaultIfBlank(filter.getPlatformId(), StringUtils.EMPTY))) {
            return false;
        }
        if (StringUtils.isNotBlank(filter.getTitle()) && !StringUtils.containsIgnoreCase(resource.getTitle(),
                StringUtils.defaultIfBlank(filter.getTitle(), StringUtils.EMPTY))) {
            return false;
        }
        if (StringUtils.isNotBlank(filter.getTopicId())) {
            Set<Topic> topics = resource.getTopics();
            return topics.stream().anyMatch(topic -> StringUtils.equals(topic.getId().toString(), filter.getTopicId()));
        }
        return true; // if all filters match
    }

    @DgsMutation
    public Long addResource(@InputArgument(name = "resource") ResourceInput input) {
        Resource resource = resourceService.addResource(input);
        return resource.getId();
    }

    @DgsMutation
    public Resource updateResource(@InputArgument(name = "resource") ResourceInput input) {
        Resource resource = resourceService.updateResource(input);
        if (resource != null) {
            return resource;
        } else {
            throw new ResourceNotFoundException("Resource ID: " + input.getId() + " not Found!");
        }
    }

    @DgsMutation
    public Long deleteResource(@InputArgument(name = "resourceId") Long resourceId) {
        Resource existingResource = resourceService.findById(resourceId).orElse(null);
        if (existingResource != null) {
            return resourceService.deleteResource(resourceId);
        } else {
            throw new ResourceNotFoundException("Resource ID: " + resourceId + " not found");
        }
    }

    @DgsMutation
    public Long markResourceCompleted(@InputArgument(name = "id") Long id, @InputArgument(name = "completed") Boolean isCompleted) {
        return resourceService.markResourceCompleted(id, isCompleted);
    }
}
