package com.learning.resourcelibrary.resolvers;

import com.learning.resourcelibrary.exception.ResourceExistsException;
import com.learning.resourcelibrary.exception.ResourceNotFoundException;
import com.learning.resourcelibrary.models.Topic;
import com.learning.resourcelibrary.repositories.TopicRepository;
import com.netflix.graphql.dgs.*;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

@DgsComponent
public class TopicDataResolver {
    private final TopicRepository topicRepository;

    public TopicDataResolver(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @DgsQuery
    public List<Topic> allTopics() {
        return (List<Topic>) topicRepository.findAll();
    }

    // @DgsData(parentType = "Mutation", field = "addTopic")
    @DgsMutation
    public Long addTopic(@InputArgument(name = "topic") Topic topic) {
        Topic newTopic = new Topic(topic.getName(), topic.getDescription());
        Boolean topicIsExisting = topicRepository.existsByName(topic.getName());
        if (topicIsExisting) {
            throw new ResourceExistsException("Topic Name " + topic.getName() + " is already existing");
        } else {
            topicRepository.save(newTopic);
            return newTopic.getId();
        }
    }

    @DgsMutation
    public Topic updateTopic(@InputArgument(name = "topic") Topic topic) {
        Topic existingTopic = topicRepository.findById(topic.getId()).orElse(null);
        if (existingTopic != null) {
            existingTopic.setName(topic.getName());
            existingTopic.setDescription(topic.getDescription());
            topicRepository.save(existingTopic);
            return existingTopic;
        } else {
            throw new ResourceNotFoundException("Topic ID: " + topic.getId() + " not found");
        }
    }

    @DgsMutation
    public Long deleteTopic(@InputArgument(name = "topicId") Long topicId) {
        Topic existingTopic = topicRepository.findById(topicId).orElse(null);
        if (existingTopic != null) {
            try {
                topicRepository.deleteById(topicId);
                return topicId;
            } catch (DataIntegrityViolationException e) {
                throw new ResourceExistsException("Topic has Existing Resources!");
            }
        } else {
            throw new ResourceNotFoundException("Topic ID: " + topicId + " not found");
        }
    }
}
