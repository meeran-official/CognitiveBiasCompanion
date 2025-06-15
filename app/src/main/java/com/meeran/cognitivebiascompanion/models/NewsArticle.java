package com.meeran.cognitivebiascompanion.models;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class NewsArticle {
    private String id;
    private String title;
    private String description;
    private String content;
    private String url;
    
    @SerializedName("urlToImage")
    private String imageUrl;
    
    private String author;
    
    @SerializedName("source")
    private Source source;
    
    @SerializedName("publishedAt")
    private String publishedAt; // Change from Date to String to match API
    
    private double biasScore; // 0.0 - 1.0
    private String biasCategory; // "left", "center", "right", "mixed"
    private boolean isAnalyzed;

    public NewsArticle() {}

    public NewsArticle(String id, String title, String description, String content, String url,
                      String imageUrl, String author, String sourceName, String publishedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.url = url;
        this.imageUrl = imageUrl;
        this.author = author;
        this.source = new Source(sourceName);
        this.publishedAt = publishedAt;
        this.isAnalyzed = false;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Source getSource() { return source; }
    public void setSource(Source source) { this.source = source; }

    public String getPublishedAt() { return publishedAt; }
    public void setPublishedAt(String publishedAt) { this.publishedAt = publishedAt; }

    public double getBiasScore() { return biasScore; }
    public void setBiasScore(double biasScore) { this.biasScore = biasScore; }

    public String getBiasCategory() { return biasCategory; }
    public void setBiasCategory(String biasCategory) { this.biasCategory = biasCategory; }

    public boolean isAnalyzed() { return isAnalyzed; }
    public void setAnalyzed(boolean analyzed) { isAnalyzed = analyzed; }
    
    // Inner class for Source
    public static class Source {
        private String id;
        private String name;
        
        public Source() {}
        
        public Source(String name) {
            this.name = name;
        }
        
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}
