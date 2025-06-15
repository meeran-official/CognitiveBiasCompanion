package com.meeran.cognitivebiascompanion.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meeran.cognitivebiascompanion.R;
import com.meeran.cognitivebiascompanion.models.CognitiveBias;

import java.util.List;

public class BiasAdapter extends RecyclerView.Adapter<BiasAdapter.BiasViewHolder> {
    private List<CognitiveBias> biases;

    public BiasAdapter(List<CognitiveBias> biases) {
        this.biases = biases;
    }

    @NonNull
    @Override
    public BiasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bias_card, parent, false);
        return new BiasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BiasViewHolder holder, int position) {
        CognitiveBias bias = biases.get(position);
        holder.bind(bias);
    }

    @Override
    public int getItemCount() {
        return biases != null ? biases.size() : 0;
    }

    public void updateBiases(List<CognitiveBias> newBiases) {
        this.biases = newBiases;
        notifyDataSetChanged();
    }    static class BiasViewHolder extends RecyclerView.ViewHolder {
        private TextView biasName, biasDescription, biasCategory, biasImpact;

        public BiasViewHolder(@NonNull View itemView) {
            super(itemView);
            biasName = itemView.findViewById(R.id.bias_name);
            biasDescription = itemView.findViewById(R.id.bias_description);
            biasCategory = itemView.findViewById(R.id.bias_category);
            biasImpact = itemView.findViewById(R.id.bias_impact);
        }

        public void bind(CognitiveBias bias) {
            biasName.setText(bias.getName());
            biasDescription.setText(bias.getDescription());
            biasCategory.setText(bias.getCategory());
            biasImpact.setText("Impact: " + bias.getSeverity() + "/10");
        }
    }
}
