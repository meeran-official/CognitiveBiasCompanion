package com.meeran.cognitivebiascompanion.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GeminiApiService {    @Headers("Content-Type: application/json")
    @POST("models/gemini-2.5-flash-preview-05-20:generateContent")
    Call<GeminiResponse> analyzeBias(
        @Query("key") String apiKey,
        @Body GeminiRequest request
    );

    class GeminiRequest {
        private Content[] contents;

        public GeminiRequest(Content[] contents) {
            this.contents = contents;
        }

        public Content[] getContents() { return contents; }
        public void setContents(Content[] contents) { this.contents = contents; }

        public static class Content {
            private Part[] parts;

            public Content(Part[] parts) {
                this.parts = parts;
            }

            public Part[] getParts() { return parts; }
            public void setParts(Part[] parts) { this.parts = parts; }

            public static class Part {
                private String text;

                public Part(String text) {
                    this.text = text;
                }

                public String getText() { return text; }
                public void setText(String text) { this.text = text; }
            }
        }
    }

    class GeminiResponse {
        private Candidate[] candidates;

        public Candidate[] getCandidates() { return candidates; }
        public void setCandidates(Candidate[] candidates) { this.candidates = candidates; }

        public class Candidate {
            private Content content;

            public Content getContent() { return content; }
            public void setContent(Content content) { this.content = content; }

            public class Content {
                private Part[] parts;

                public Part[] getParts() { return parts; }
                public void setParts(Part[] parts) { this.parts = parts; }

                public class Part {
                    private String text;

                    public String getText() { return text; }
                    public void setText(String text) { this.text = text; }
                }
            }
        }
    }
}
