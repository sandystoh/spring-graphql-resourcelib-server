package com.learning.resourcelibrary.services;

import com.learning.resourcelibrary.models.Resource;
import com.learning.resourcelibrary.repositories.PlatformRepository;
import com.learning.resourcelibrary.repositories.ResourceRepository;
import com.learning.resourcelibrary.repositories.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ResourceServiceImplTest {

    ResourceServiceImpl resourceService;

    @Mock
    ResourceRepository resourceRepository;

    @Mock
    AuthorService authorService;

    @Mock
    PlatformRepository platformRepository;

    @Mock
    TopicRepository topicRepository;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        resourceService = new ResourceServiceImpl(resourceRepository, authorService, platformRepository, topicRepository);
    }

    @Test
    void getResourcesTest() {
        Resource resource = new Resource();
        List<Resource> resourceData = new ArrayList();
        resourceData.add(resource);

        when(resourceService.getResources()).thenReturn(resourceData);

        List<Resource> resources = resourceService.getResources();

        assertEquals(resources.size(), 1);
        verify(resourceRepository, times(1)).findAll();
        verify(resourceRepository, never()).findById(anyLong());
    }

    @Test
    void getResourcesByIdTest() {
        Resource Resource = new Resource();
        Resource.setId(1L);
        Optional<Resource> resourceOptional = Optional.of(Resource);

        when(resourceRepository.findById(anyLong())).thenReturn(resourceOptional);

        Optional<Resource> resourceReturned = resourceService.findById(1L);


        assertNotNull(resourceReturned);
        verify(resourceRepository, times(1)).findById(anyLong());
        verify(resourceRepository, never()).findAll();
    }
}