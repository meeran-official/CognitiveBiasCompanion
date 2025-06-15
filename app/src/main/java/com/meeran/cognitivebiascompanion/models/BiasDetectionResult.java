package com.meeran.cognitivebiascompanion.models;

import java.util.List;

public class BiasDetectionResult {
    private List<DetectedBias> biases_detected;
    private double overall_bias_score;
    private String summary;
    private String recommendations;

    public BiasDetectionResult() {}

    public List<DetectedBias> getBiasesDetected() { return biases_detected; }
    public void setBiasesDetected(List<DetectedBias> biases_detected) { this.biases_detected = biases_detected; }

    public double getOverallBiasScore() { return overall_bias_score; }
    public void setOverallBiasScore(double overall_bias_score) { this.overall_bias_score = overall_bias_score; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getRecommendations() { return recommendations; }
    public void setRecommendations(String recommendations) { this.recommendations = recommendations; }

    public static class DetectedBias {
        private String name;
        private double confidence;
        private String explanation;
        private String text_snippet;

        public DetectedBias() {}

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public double getConfidence() { return confidence; }
        public void setConfidence(double confidence) { this.confidence = confidence; }

        public String getExplanation() { return explanation; }
        public void setExplanation(String explanation) { this.explanation = explanation; }

        public String getTextSnippet() { return text_snippet; }
        public void setTextSnippet(String text_snippet) { this.text_snippet = text_snippet; }
    }
}
