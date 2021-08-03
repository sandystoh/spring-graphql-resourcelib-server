package com.learning.resourcelibrary.filters;

import com.learning.resourcelibrary.models.Type;

public class ResourceFilter {
    private String type;
    private String platformId;
    private String topicId;
    private String title;

    public ResourceFilter() {
    }

    @Override
    public String toString() {
        return "ResourceFilter{" +
                "type='" + type + '\'' +
                ", platformId='" + platformId + '\'' +
                ", topicId='" + topicId + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
