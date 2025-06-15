package com.meeran.cognitivebiascompanion.models;

import java.util.Date;
import java.util.List;

public class BiasAnalysis {
    private String id;
    private String text;
    private String source; // "news", "social_media", "user_input"
    private List<DetectedBias> detectedBiases;
    private double overallBiasScore; // 0.0 - 1.0
    private String summary;
    private Date timestamp;
    private String userId;

    public BiasAnalysis() {}

    public BiasAnalysis(String id, String text, String source, List<DetectedBias> detectedBiases,
                       double overallBiasScore, String summary, Date timestamp, String userId) {
        this.id = id;
        this.text = text;
        this.source = source;
        this.detectedBiases = detectedBiases;
        this.overallBiasScore = overallBiasScore;
        this.summary = summary;
        this.timestamp = timestamp;
        this.userId = userId;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public List<DetectedBias> getDetectedBiases() { return detectedBiases; }
    public void setDetectedBiases(List<DetectedBias> detectedBiases) { this.detectedBiases = detectedBiases; }

    public double getOverallBiasScore() { return overallBiasScore; }
    public void setOverallBiasScore(double overallBiasScore) { this.overallBiasScore = overallBiasScore; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public static class DetectedBias {
        private String biasId;
        private String biasName;
        private double confidence; // 0.0 - 1.0
        private String explanation;
        private int startPosition;
        private int endPosition;

        public DetectedBias() {}

        public DetectedBias(String biasId, String biasName, double confidence, 
                          String explanation, int startPosition, int endPosition) {
            this.biasId = biasId;
            this.biasName = biasName;
            this.confidence = confidence;
            this.explanation = explanation;
            this.startPosition = startPosition;
            this.endPosition = endPosition;
        }

        // Getters and Setters
        public String getBiasId() { return biasId; }
        public void setBiasId(String biasId) { this.biasId = biasId; }

        public String getBiasName() { return biasName; }
        public void setBiasName(String biasName) { this.biasName = biasName; }

        public double getConfidence() { return confidence; }
        public void setConfidence(double confidence) { this.confidence = confidence; }

        public String getExplanation() { return explanation; }
        public void setExplanation(String explanation) { this.explanation = explanation; }

        public int getStartPosition() { return startPosition; }
        public void setStartPosition(int startPosition) { this.startPosition = startPosition; }

        public int getEndPosition() { return endPosition; }
        public void setEndPosition(int endPosition) { this.endPosition = endPosition; }
    }
}
