package com.meeran.cognitivebiascompanion.models;

import java.util.List;

public class QuizQuestion {
    private String question;
    private List<String> options;
    private int correctAnswerIndex;
    private String explanation;
    private String biasType;

    public QuizQuestion(String question, List<String> options, int correctAnswerIndex, String explanation, String biasType) {
        this.question = question;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
        this.explanation = explanation;
        this.biasType = biasType;
    }

    // Getters
    public String getQuestion() { return question; }
    public List<String> getOptions() { return options; }
    public int getCorrectAnswerIndex() { return correctAnswerIndex; }
    public String getExplanation() { return explanation; }
    public String getBiasType() { return biasType; }
}
