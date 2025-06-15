package com.meeran.cognitivebiascompanion.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.meeran.cognitivebiascompanion.R;
import com.meeran.cognitivebiascompanion.models.NewsArticle;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<NewsArticle> articles;
    private OnNewsActionListener listener;

    public interface OnNewsActionListener {
        void onAnalyzeBias(NewsArticle article);
    }

    public NewsAdapter(List<NewsArticle> articles) {
        this.articles = articles;
    }

    public void setOnNewsActionListener(OnNewsActionListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news_card, parent, false);
        return new NewsViewHolder(view);
    }    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsArticle article = articles.get(position);
        holder.bind(article, listener);
    }

    @Override
    public int getItemCount() {
        return articles != null ? articles.size() : 0;
    }

    public void updateArticles(List<NewsArticle> newArticles) {
        this.articles = newArticles;
        notifyDataSetChanged();
    }    static class NewsViewHolder extends RecyclerView.ViewHolder {
        private TextView titleText, descriptionText, sourceText, publishedText;
        private MaterialButton analyzeBiasButton;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.news_title);
            descriptionText = itemView.findViewById(R.id.news_description);
            sourceText = itemView.findViewById(R.id.news_source);
            publishedText = itemView.findViewById(R.id.news_published);
            analyzeBiasButton = itemView.findViewById(R.id.analyze_bias_button);
        }        public void bind(NewsArticle article, OnNewsActionListener listener) {
            titleText.setText(article.getTitle() != null ? article.getTitle() : "No Title");
            descriptionText.setText(article.getDescription() != null ? article.getDescription() : "No Description");
            sourceText.setText(article.getSource() != null && article.getSource().getName() != null ? 
                             article.getSource().getName() : "Unknown Source");
            
            // Handle date formatting for string publishedAt
            if (article.getPublishedAt() != null && !article.getPublishedAt().isEmpty()) {
                try {
                    // News API returns ISO 8601 format: "2023-06-15T08:03:00Z"
                    // For now, just display a simplified version
                    String dateStr = article.getPublishedAt().replace("T", " ").replace("Z", "");
                    if (dateStr.length() > 16) {
                        dateStr = dateStr.substring(0, 16); // Show only date and time, not seconds
                    }
                    publishedText.setText(dateStr);
                } catch (Exception e) {
                    publishedText.setText(article.getPublishedAt());
                }
            } else {
                publishedText.setText("Unknown Date");
            }

            // Setup bias analysis button
            if (analyzeBiasButton != null && listener != null) {
                analyzeBiasButton.setOnClickListener(v -> {
                    Toast.makeText(v.getContext(), "🧠 Analyzing article for bias...", Toast.LENGTH_SHORT).show();
                    listener.onAnalyzeBias(article);
                });
            }
        }
    }
}
