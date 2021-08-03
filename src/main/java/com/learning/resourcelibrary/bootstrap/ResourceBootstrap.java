package com.learning.resourcelibrary.bootstrap;

import com.learning.resourcelibrary.models.*;
import com.learning.resourcelibrary.repositories.AuthorRepository;
import com.learning.resourcelibrary.repositories.PlatformRepository;
import com.learning.resourcelibrary.repositories.ResourceRepository;
import com.learning.resourcelibrary.repositories.TopicRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ResourceBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final ResourceRepository resourceRepository;
    private final AuthorRepository authorRepository;
    private final TopicRepository topicRepository;
    private final PlatformRepository platformRepository;

    public ResourceBootstrap(ResourceRepository resourceRepository, AuthorRepository authorRepository, TopicRepository topicRepository, PlatformRepository platformRepository) {
        this.resourceRepository = resourceRepository;
        this.authorRepository = authorRepository;
        this.topicRepository = topicRepository;
        this.platformRepository = platformRepository;
    }

    private Author getAuthor (String name){
        return authorRepository
                .findByName(name)
                .orElseThrow(() -> new RuntimeException("Topic" + name + " not found"));
    }

    private Topic getTopic (String name){
        return topicRepository
                .findByName(name)
                .orElseThrow(() -> new RuntimeException("Topic" + name + " not found"));
    }

    private Platform getPlatform (String name){
        return platformRepository
                .findByName(name)
                .orElseThrow(() -> new RuntimeException("Platform" + name + " not found"));
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        resourceRepository.saveAll(getResources());
    }

    private List<Resource> getResources() {
        List<Resource> resources = new ArrayList<>(2);
        Platform udemy = getPlatform("Udemy");
        Platform pluralsight = getPlatform("PluralSight");
        Topic reactTopic = getTopic("React");
        Topic graphqlTopic = getTopic("GraphQL");
        Topic springboot = getTopic("Spring Boot");
        Author max = getAuthor("Maximilian Schwarzmüller");
        Author esteban = getAuthor("Esteban Herrera");

        Resource react = new Resource();
        react.setTitle("React - The Complete Guide (incl Hooks, React Router, Redux)");
        react.setAuthor(max);
        react.setType(Type.COURSE);
        react.setPlatform(udemy);
        react.setUrl("https://www.udemy.com/course/react-the-complete-guide-incl-redux/");
        react.addTopic(reactTopic);
        // resources.add(react);

        Resource graphql = new Resource();
        graphql.setTitle("Building a GraphQL Server with Spring Boot");
        graphql.setAuthor(esteban);
        graphql.setType(Type.GUIDE);
        graphql.setPlatform(pluralsight);
        graphql.setUrl("https://www.pluralsight.com/guides/building-a-graphql-server-with-spring-boot");
        graphql.addTopic(graphqlTopic);
        graphql.addTopic(springboot);
        // resources.add(graphql);

        return resources;
    }
}
