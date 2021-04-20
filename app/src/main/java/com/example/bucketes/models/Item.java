package com.example.bucketes.models;

public class Item {
    private String username; /* reference to the user that this item belongs to */
    private String title;
    private String story;
    private String completionDate;
    private String status;  /* planned, in-progress, finished */

    public Item(String username, String title) {
        this.username = username;
        this.title = title;
    }

    /** Constructor */
    public Item(String username, String title, String story, String completionDate, String status) {
        this.username = username;
        this.title = title;
        this.story = story;
        this.completionDate = completionDate;
        this.status = status;
    }

    /** Getters and setters */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ItemModel{" +
                "username='" + username + '\'' +
                ", title='" + title + '\'' +
                ", story='" + story + '\'' +
                ", completionDate='" + completionDate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
