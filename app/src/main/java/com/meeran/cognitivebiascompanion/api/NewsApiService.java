package com.meeran.cognitivebiascompanion.api;

import com.meeran.cognitivebiascompanion.models.NewsArticle;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiService {
    
    @GET("everything")
    Call<NewsResponse> searchNews(
            @Query("q") String query,
            @Query("apiKey") String apiKey,
            @Query("language") String language,
            @Query("sortBy") String sortBy,
            @Query("pageSize") int pageSize
    );

    @GET("top-headlines")
    Call<NewsResponse> getTopHeadlines(
            @Query("country") String country,
            @Query("category") String category,
            @Query("apiKey") String apiKey,
            @Query("pageSize") int pageSize
    );

    class NewsResponse {
        private String status;
        private int totalResults;
        private List<NewsArticle> articles;

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public int getTotalResults() { return totalResults; }
        public void setTotalResults(int totalResults) { this.totalResults = totalResults; }

        public List<NewsArticle> getArticles() { return articles; }
        public void setArticles(List<NewsArticle> articles) { this.articles = articles; }
    }
}
