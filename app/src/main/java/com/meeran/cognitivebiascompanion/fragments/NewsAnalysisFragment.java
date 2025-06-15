package com.meeran.cognitivebiascompanion.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meeran.cognitivebiascompanion.BuildConfig;
import com.meeran.cognitivebiascompanion.R;
import com.meeran.cognitivebiascompanion.api.ApiClient;
import com.meeran.cognitivebiascompanion.api.GeminiApiService;
import com.meeran.cognitivebiascompanion.api.NewsApiService;
import com.meeran.cognitivebiascompanion.models.NewsArticle;
import com.meeran.cognitivebiascompanion.adapters.NewsAdapter;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsAnalysisFragment extends Fragment implements NewsAdapter.OnNewsActionListener {
    private static final String TAG = "NewsAnalysisFragment";    private RecyclerView newsRecyclerView;
    private View progressIndicator;
    private Button testConnectionButton;
    private NewsAdapter newsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_analysis, container, false);
        
        initViews(view);
        setupRecyclerView();
        loadLatestNews();
        
        return view;
    }    private void initViews(View view) {
        newsRecyclerView = view.findViewById(R.id.news_recyclerview);
        progressIndicator = view.findViewById(R.id.progress_indicator);
        testConnectionButton = view.findViewById(R.id.btn_test_connection);
        
        testConnectionButton.setOnClickListener(v -> testApiConnection());
    }

    private void setupRecyclerView() {
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }    private void loadLatestNews() {
        // Check network connectivity first
        if (!isNetworkAvailable()) {
            showProgress(false);
            Toast.makeText(getContext(), "❌ No internet connection\n📱 Loading sample data instead...", Toast.LENGTH_LONG).show();
            loadSampleNews();
            return;
        }

        Toast.makeText(getContext(), "📡 Loading latest news...", Toast.LENGTH_SHORT).show();
        showProgress(true);
        
        String apiKey = BuildConfig.NEWS_API_KEY;
        Log.d(TAG, "API Key length: " + (apiKey != null ? apiKey.length() : 0));
        
        if (TextUtils.isEmpty(apiKey) || apiKey.equals("YOUR_NEWS_API_KEY_HERE")) {
            showProgress(false);
            Toast.makeText(getContext(), "❌ Please configure your News API key in local.properties\nVisit: https://newsapi.org to get a free API key", Toast.LENGTH_LONG).show();
            loadSampleNews();
            return;
        }        Log.d(TAG, "Starting API call to NewsAPI...");
        String fullUrl = "https://newsapi.org/v2/top-headlines?country=us&category=technology&pageSize=10&apiKey=" + 
                         apiKey.substring(0, Math.min(8, apiKey.length())) + "...";
        Log.d(TAG, "Request URL (partial): " + fullUrl);
        
        // Try with a simpler API call first
        ApiClient.getNewsApiService().getTopHeadlines("us", "technology", apiKey, 10)
                .enqueue(new Callback<NewsApiService.NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsApiService.NewsResponse> call, Response<NewsApiService.NewsResponse> response) {
                        Log.d(TAG, "API Response received. Code: " + response.code());
                        showProgress(false);
                        if (response.isSuccessful() && response.body() != null) {
                            Log.d(TAG, "Successful response with " + response.body().getArticles().size() + " articles");
                            Toast.makeText(getContext(), "✅ Received news response from API!", Toast.LENGTH_SHORT).show();
                            handleNewsResponse(response.body().getArticles());
                        } else {
                            String errorMsg = "❌ Failed to load news. Response code: " + response.code();
                            Log.e(TAG, "API Error: " + errorMsg);
                            if (response.code() == 401) {
                                errorMsg += "\n🔑 Check your News API key";
                            } else if (response.code() == 429) {
                                errorMsg += "\n⏱️ API rate limit exceeded";
                            }
                            Toast.makeText(getContext(), errorMsg + "\n📱 Loading sample data instead...", Toast.LENGTH_LONG).show();
                            loadSampleNews();
                        }
                    }                    @Override
                    public void onFailure(Call<NewsApiService.NewsResponse> call, Throwable t) {
                        Log.e(TAG, "API Failure Details:", t);
                        Log.e(TAG, "Exception type: " + t.getClass().getSimpleName());
                        Log.e(TAG, "Exception message: " + t.getMessage());
                        if (t.getCause() != null) {
                            Log.e(TAG, "Exception cause: " + t.getCause().getMessage());
                        }
                        
                        showProgress(false);
                        String errorMsg = "❌ Network error: " + t.getClass().getSimpleName();
                        
                        if (t.getMessage() != null) {
                            if (t.getMessage().contains("timeout")) {
                                errorMsg += "\n🌐 Connection timeout - try again later";
                            } else if (t.getMessage().contains("Unable to resolve host")) {
                                errorMsg += "\n🌐 DNS resolution failed - check internet connection";
                            } else if (t.getMessage().contains("ConnectException")) {
                                errorMsg += "\n🌐 Connection refused - server may be down";
                            } else if (t.getMessage().contains("SSLException")) {
                                errorMsg += "\n🔒 SSL/Certificate error";
                            } else if (t.getMessage().contains("SocketException")) {
                                errorMsg += "\n📡 Socket connection error";
                            } else {
                                errorMsg += "\n📝 " + t.getMessage();
                            }
                        }
                        Toast.makeText(getContext(), errorMsg + "\n📱 Loading sample data instead...", Toast.LENGTH_LONG).show();
                        
                        // Load sample data as fallback
                        loadSampleNews();
                    }
                });
    }    private void handleNewsResponse(List<NewsArticle> articles) {
        if (articles != null && !articles.isEmpty()) {
            if (newsAdapter == null) {
                newsAdapter = new NewsAdapter(articles);
                newsAdapter.setOnNewsActionListener(this); // Enable bias analysis
                newsRecyclerView.setAdapter(newsAdapter);
            } else {
                newsAdapter.updateArticles(articles);
            }
            Toast.makeText(getContext(), "📰 Loaded and displayed " + articles.size() + " articles successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "❌ No articles found", Toast.LENGTH_SHORT).show();
        }
    }private void showProgress(boolean show) {
        progressIndicator.setVisibility(show ? View.VISIBLE : View.GONE);
        newsRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = 
            (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }        return false;
    }

    private void testApiConnection() {
        Log.d(TAG, "Testing API connection...");
        
        // Test 1: Network connectivity
        if (!isNetworkAvailable()) {
            Toast.makeText(getContext(), "❌ No network connection available", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(getContext(), "✅ Network connection available", Toast.LENGTH_SHORT).show();
        
        // Test 2: API Key
        String apiKey = BuildConfig.NEWS_API_KEY;
        if (TextUtils.isEmpty(apiKey) || apiKey.equals("YOUR_NEWS_API_KEY_HERE")) {
            Toast.makeText(getContext(), "❌ API key not configured", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(getContext(), "✅ API key configured (length: " + apiKey.length() + ")", Toast.LENGTH_SHORT).show();
        
        // Test 3: Try simple API call
        Toast.makeText(getContext(), "🔄 Testing API call...", Toast.LENGTH_SHORT).show();
        loadLatestNews();
    }private void loadSampleNews() {
        Log.d(TAG, "Loading sample news data...");
        List<NewsArticle> sampleArticles = new ArrayList<>();
        
        sampleArticles.add(new NewsArticle(
            "1", "AI Breakthrough in Cognitive Bias Detection", 
            "Researchers develop new AI model that can identify cognitive biases in real-time text analysis.",
            "Full article content here...", "https://example.com/article1", "", 
            "Tech Reporter", "TechNews Daily", "2025-06-15T08:03:00Z"
        ));
        
        sampleArticles.add(new NewsArticle(
            "2", "Psychology Study Reveals New Confirmation Bias Patterns", 
            "Latest research shows how confirmation bias manifests differently across age groups.",
            "Full article content here...", "https://example.com/article2", "", 
            "Dr. Smith", "Psychology Today", "2025-06-15T07:30:00Z"
        ));
        
        sampleArticles.add(new NewsArticle(
            "3", "Media Literacy Programs Combat Information Bias", 
            "Schools implementing new curricula to help students identify and overcome cognitive biases.",
            "Full article content here...", "https://example.com/article3", "", 
            "Education Writer", "Education Weekly", "2025-06-15T06:45:00Z"
        ));
        
        sampleArticles.add(new NewsArticle(
            "4", "Social Media Algorithms and Cognitive Bias Research", 
            "New study examines how social media recommendation algorithms may amplify cognitive biases.",
            "Full article content here...", "https://example.com/article4", "", 
            "Data Scientist", "Digital Trends", "2025-06-15T06:00:00Z"
        ));
        
        sampleArticles.add(new NewsArticle(
            "5", "Corporate Decision Making and Bias Mitigation", 
            "Fortune 500 companies implementing bias-aware decision making frameworks.",
            "Full article content here...", "https://example.com/article5", "", 
            "Business Reporter", "Business Insider", "2025-06-15T05:15:00Z"
        ));
          handleNewsResponse(sampleArticles);
        Log.d(TAG, "Sample news data loaded successfully");
    }

    @Override
    public void onAnalyzeBias(NewsArticle article) {
        String geminiApiKey = BuildConfig.GEMINI_API_KEY;
        if (TextUtils.isEmpty(geminiApiKey)) {
            Toast.makeText(getContext(), "❌ Please configure your Gemini API key for bias analysis", Toast.LENGTH_LONG).show();
            return;
        }

        // Create analysis text from article
        String analysisText = article.getTitle() + "\n\n" + article.getDescription();
        
        Toast.makeText(getContext(), "🧠 Analyzing article bias with AI...", Toast.LENGTH_SHORT).show();
        
        // Create Gemini request for bias analysis
        String prompt = createNewsAnalysisPrompt(analysisText, article.getSource().getName());
        
        GeminiApiService.GeminiRequest.Content.Part part = 
            new GeminiApiService.GeminiRequest.Content.Part(prompt);
        GeminiApiService.GeminiRequest.Content content = 
            new GeminiApiService.GeminiRequest.Content(new GeminiApiService.GeminiRequest.Content.Part[]{part});
        GeminiApiService.GeminiRequest request = 
            new GeminiApiService.GeminiRequest(new GeminiApiService.GeminiRequest.Content[]{content});

        ApiClient.getGeminiService().analyzeBias(geminiApiKey, request).enqueue(new Callback<GeminiApiService.GeminiResponse>() {
            @Override
            public void onResponse(Call<GeminiApiService.GeminiResponse> call, Response<GeminiApiService.GeminiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String analysisResult = response.body().getCandidates()[0].getContent().getParts()[0].getText();
                    showBiasAnalysisDialog(article, analysisResult);
                } else {
                    Toast.makeText(getContext(), "❌ Bias analysis failed. Response code: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GeminiApiService.GeminiResponse> call, Throwable t) {
                Toast.makeText(getContext(), "❌ Network error during bias analysis: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private String createNewsAnalysisPrompt(String articleText, String sourceName) {
        return "You are an expert media bias analyst. Analyze this news article for potential biases.\n\n" +
               "Article Source: " + sourceName + "\n\n" +
               "Article Text:\n" + articleText + "\n\n" +
               "Please provide a brief analysis (2-3 sentences) covering:\n" +
               "1. Overall bias assessment (left/right/center)\n" +
               "2. Key bias indicators found\n" +
               "3. Reliability of the source\n" +
               "4. Factual vs opinion content ratio\n\n" +
               "Keep the analysis concise and actionable for general readers.";
    }

    private void showBiasAnalysisDialog(NewsArticle article, String analysisResult) {
        // For now, show in a toast - in production you'd show a proper dialog
        String shortResult = analysisResult.length() > 200 ? 
            analysisResult.substring(0, 200) + "..." : analysisResult;
        
        Toast.makeText(getContext(), 
            "📊 Bias Analysis for \"" + article.getTitle() + "\"\n\n" + shortResult, 
            Toast.LENGTH_LONG).show();
    }
}
