package com.meeran.cognitivebiascompanion.models;

import java.util.List;

public class CognitiveBias {
    private String id;
    private String name;
    private String description;
    private String category;
    private List<String> examples;
    private String prevention;
    private int severity; // 1-10 scale
    private String imageUrl;
    private boolean isFavorite;

    public CognitiveBias() {}

    public CognitiveBias(String id, String name, String description, String category, 
                        List<String> examples, String prevention, int severity, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.examples = examples;
        this.prevention = prevention;
        this.severity = severity;
        this.imageUrl = imageUrl;
        this.isFavorite = false;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public List<String> getExamples() { return examples; }
    public void setExamples(List<String> examples) { this.examples = examples; }

    public String getPrevention() { return prevention; }
    public void setPrevention(String prevention) { this.prevention = prevention; }

    public int getSeverity() { return severity; }
    public void setSeverity(int severity) { this.severity = severity; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
}
