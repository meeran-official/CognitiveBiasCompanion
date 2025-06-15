package com.meeran.cognitivebiascompanion.api;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;

public class ApiClient {
    private static final String TAG = "ApiClient";
    private static final String NEWS_API_BASE_URL = "https://newsapi.org/v2/";
    private static final String OPENAI_API_BASE_URL = "https://api.openai.com/v1/";
    private static final String GEMINI_API_BASE_URL = "https://generativelanguage.googleapis.com/v1beta/";
    
    private static Retrofit newsApiRetrofit;
    private static Retrofit openAIRetrofit;
    private static Retrofit geminiRetrofit;    public static NewsApiService getNewsApiService() {
        if (newsApiRetrofit == null) {
            Log.d(TAG, "Creating NewsAPI Retrofit client...");
            
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();

            newsApiRetrofit = new Retrofit.Builder()
                    .baseUrl(NEWS_API_BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
                    
            Log.d(TAG, "NewsAPI Retrofit client created successfully");
        }
        return newsApiRetrofit.create(NewsApiService.class);
    }

    public static GeminiApiService getGeminiService() {
        if (geminiRetrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build();

            geminiRetrofit = new Retrofit.Builder()
                    .baseUrl(GEMINI_API_BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return geminiRetrofit.create(GeminiApiService.class);
    }

    public static OpenAIService getOpenAIService(String apiKey) {
        if (openAIRetrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        return chain.proceed(
                            chain.request()
                                .newBuilder()
                                .addHeader("Authorization", "Bearer " + apiKey)
                                .build()
                        );
                    })
                    .addInterceptor(logging)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build();

            openAIRetrofit = new Retrofit.Builder()
                    .baseUrl(OPENAI_API_BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return openAIRetrofit.create(OpenAIService.class);
    }
}
