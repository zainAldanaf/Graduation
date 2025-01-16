package com.example.palliativecareguidelines.modules;

import android.net.Uri;

public class Topics {
    public String getTopic_title() {
        return topic_title;
    }

    public void setTopic_title(String topic_title) {
        this.topic_title = topic_title;
    }

    public String getTopic_content() {
        return topic_content;
    }

    public void setTopic_content(String topic_content) {
        this.topic_content = topic_content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String topic_title;
    private String topic_video;

    public Topics(String id,String topic_title, String topic_content, String image,String topic_video) {
        this.topic_title = topic_title;
        this.topic_content = topic_content;
        this.image = image;
        this.id=id;
        this.topic_video=topic_video;
    }

    private String topic_content;
    private String image;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
}
