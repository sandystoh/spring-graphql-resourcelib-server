package com.learning.resourcelibrary.resolvers;

import com.learning.resourcelibrary.exception.ResourceExistsException;
import com.learning.resourcelibrary.exception.ResourceNotFoundException;
import com.learning.resourcelibrary.models.Platform;
import com.learning.resourcelibrary.models.Topic;
import com.learning.resourcelibrary.repositories.PlatformRepository;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

@DgsComponent
public class PlatformDataResolver {
    private final PlatformRepository platformRepository;

    public PlatformDataResolver(PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }

    @DgsQuery
    public List<Platform> allPlatforms() {
        return (List<Platform>) platformRepository.findAll();
    }

    @DgsMutation
    public Long addPlatform(@InputArgument(name = "platform") Platform platform) {
        Platform newPlatform = new Platform(platform.getName(), platform.getUrl());
        Boolean topicIsExisting = platformRepository.existsByName(platform.getName());
        if (topicIsExisting) {
            throw new ResourceExistsException("Platform Name " + platform.getName() + " is already existing");
        } else {
            platformRepository.save(newPlatform);
            return newPlatform.getId();
        }
    }

    @DgsMutation
    public Platform updatePlatform(@InputArgument(name = "platform") Platform platform) {
        Platform existingPlatform = platformRepository.findById(platform.getId()).orElse(null);
        if (existingPlatform != null) {
            existingPlatform.setName(platform.getName());
            existingPlatform.setUrl(platform.getUrl());
            platformRepository.save(existingPlatform);
            return existingPlatform;
        } else {
            throw new ResourceNotFoundException("Platform ID: " + platform.getId() + " not found");
        }
    }

    @DgsMutation
    public Long deletePlatform(@InputArgument(name = "platformId") Long platformId) {
        Platform existingPlatform = platformRepository.findById(platformId).orElse(null);
        if (existingPlatform != null) {
            try {
                platformRepository.deleteById(platformId);
                return platformId;
            } catch (DataIntegrityViolationException e) {
                throw new ResourceExistsException("Platform has Existing Resources!");
            }
        } else {
            throw new ResourceNotFoundException("Platform ID: " + platformId + " not found");
        }
    }
}
