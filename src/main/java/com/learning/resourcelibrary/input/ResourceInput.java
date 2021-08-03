package com.learning.resourcelibrary.input;

import com.learning.resourcelibrary.models.*;
import java.util.Set;

public class ResourceInput {
    private Long id;
    private String title;
    private String description;
    private Integer publicationYear;
    private Type type;
    private String url;
    private Float length;
    private Float rating;
    private Boolean completed = false;
    private Level level = Level.ALL;
    private Long platformId;
    private String authorName;
    private Set<Long> topics;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public Type getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public Float getLength() {
        return length;
    }

    public Float getRating() {
        return rating;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public Level getLevel() {
        return level;
    }

    public Long getPlatformId() {
        return platformId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Set<Long> getTopics() {
        return topics;
    }
}
