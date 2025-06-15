package com.meeran.cognitivebiascompanion.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.meeran.cognitivebiascompanion.R;
import com.meeran.cognitivebiascompanion.models.BiasDetectionResult;

import java.util.List;
import java.util.Locale;

public class BiasResultAdapter extends RecyclerView.Adapter<BiasResultAdapter.BiasViewHolder> {
    private List<BiasDetectionResult.DetectedBias> biases;

    public BiasResultAdapter(List<BiasDetectionResult.DetectedBias> biases) {
        this.biases = biases;
    }

    @NonNull
    @Override
    public BiasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bias_result, parent, false);
        return new BiasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BiasViewHolder holder, int position) {
        BiasDetectionResult.DetectedBias bias = biases.get(position);
        holder.bind(bias);
    }

    @Override
    public int getItemCount() {
        return biases != null ? biases.size() : 0;
    }

    public void updateBiases(List<BiasDetectionResult.DetectedBias> newBiases) {
        this.biases = newBiases;
        notifyDataSetChanged();
    }

    static class BiasViewHolder extends RecyclerView.ViewHolder {
        private TextView biasNameText, confidenceText, explanationText, snippetText;
        private LinearProgressIndicator confidenceProgress;
        private MaterialCardView cardView;

        BiasViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (MaterialCardView) itemView;
            biasNameText = itemView.findViewById(R.id.bias_name_text);
            confidenceText = itemView.findViewById(R.id.confidence_text);
            explanationText = itemView.findViewById(R.id.explanation_text);
            snippetText = itemView.findViewById(R.id.snippet_text);
            confidenceProgress = itemView.findViewById(R.id.confidence_progress);
        }

        void bind(BiasDetectionResult.DetectedBias bias) {
            biasNameText.setText(bias.getName());
            confidenceText.setText(String.format(Locale.getDefault(), "%.0f%% Confidence", bias.getConfidence() * 100));
            explanationText.setText(bias.getExplanation());
            
            if (bias.getTextSnippet() != null && !bias.getTextSnippet().trim().isEmpty()) {
                snippetText.setText("\"" + bias.getTextSnippet() + "\"");
                snippetText.setVisibility(View.VISIBLE);
            } else {
                snippetText.setVisibility(View.GONE);
            }

            // Set progress bar
            int progress = (int) (bias.getConfidence() * 100);
            confidenceProgress.setProgress(progress);

            // Color coding based on confidence level
            if (progress >= 80) {
                cardView.setCardBackgroundColor(itemView.getContext().getColor(R.color.bias_high));
            } else if (progress >= 50) {
                cardView.setCardBackgroundColor(itemView.getContext().getColor(R.color.bias_medium));
            } else {
                cardView.setCardBackgroundColor(itemView.getContext().getColor(R.color.bias_low));
            }
        }
    }
}
