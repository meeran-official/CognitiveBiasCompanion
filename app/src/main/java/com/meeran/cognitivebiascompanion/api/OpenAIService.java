package com.meeran.cognitivebiascompanion.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OpenAIService {
    
    @Headers("Content-Type: application/json")
    @POST("chat/completions")
    Call<OpenAIResponse> analyzeBias(@Body OpenAIRequest request);

    class OpenAIRequest {
        private String model;
        private Message[] messages;
        private double temperature;
        private int max_tokens;

        public OpenAIRequest(String model, Message[] messages, double temperature, int max_tokens) {
            this.model = model;
            this.messages = messages;
            this.temperature = temperature;
            this.max_tokens = max_tokens;
        }

        // Getters and Setters
        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }

        public Message[] getMessages() { return messages; }
        public void setMessages(Message[] messages) { this.messages = messages; }

        public double getTemperature() { return temperature; }
        public void setTemperature(double temperature) { this.temperature = temperature; }

        public int getMax_tokens() { return max_tokens; }
        public void setMax_tokens(int max_tokens) { this.max_tokens = max_tokens; }
    }

    class Message {
        private String role;
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }

        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }

    class OpenAIResponse {
        private Choice[] choices;

        public Choice[] getChoices() { return choices; }
        public void setChoices(Choice[] choices) { this.choices = choices; }

        public class Choice {
            private Message message;

            public Message getMessage() { return message; }
            public void setMessage(Message message) { this.message = message; }
        }
    }
}
