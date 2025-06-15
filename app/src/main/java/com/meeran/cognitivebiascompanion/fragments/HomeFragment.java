package com.meeran.cognitivebiascompanion.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meeran.cognitivebiascompanion.R;
import com.meeran.cognitivebiascompanion.models.CognitiveBias;
import com.meeran.cognitivebiascompanion.adapters.BiasAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recentAnalysisRecyclerView;
    private RecyclerView featuredBiasesRecyclerView;
    private TextView welcomeTitle, welcomeSubtitle;
    private TextView dailyBiasTitle, dailyBiasDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        
        initViews(view);
        setupDailyBias();
        setupRecentAnalysis();
        setupFeaturedBiases();
        
        return view;
    }    private void initViews(View view) {
        welcomeTitle = view.findViewById(R.id.welcome_title);
        welcomeSubtitle = view.findViewById(R.id.welcome_subtitle);
        dailyBiasTitle = view.findViewById(R.id.daily_bias_title);
        dailyBiasDescription = view.findViewById(R.id.daily_bias_description);
        recentAnalysisRecyclerView = view.findViewById(R.id.recent_analysis_recyclerview);
        featuredBiasesRecyclerView = view.findViewById(R.id.featured_biases_recyclerview);        // Setup click listeners for quick action buttons
        view.findViewById(R.id.btn_quick_scan).setOnClickListener(v -> {
            Toast.makeText(getContext(), "🚀 Quick Scan clicked! Navigating...", Toast.LENGTH_SHORT).show();
            navigateToBiasScanner();
        });
        view.findViewById(R.id.btn_news_analysis).setOnClickListener(v -> {
            Toast.makeText(getContext(), "📰 News Analysis clicked! Navigating...", Toast.LENGTH_SHORT).show();
            navigateToNewsAnalysis();
        });
        
        // Add test button feedback
        view.findViewById(R.id.btn_quick_scan).setOnLongClickListener(v -> {
            Toast.makeText(getContext(), "🚀 Quick Scan button working! Tap to navigate to Bias Scanner", Toast.LENGTH_SHORT).show();
            return true;
        });
        view.findViewById(R.id.btn_news_analysis).setOnLongClickListener(v -> {
            Toast.makeText(getContext(), "📰 News Analysis button working! Tap to navigate to News Analysis", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void setupDailyBias() {
        // For now, show a static daily bias - in production this would come from API
        dailyBiasTitle.setText("Today's Bias: Confirmation Bias");
        dailyBiasDescription.setText("The tendency to search for, interpret, and recall information in a way that confirms our pre-existing beliefs.");
    }

    private void setupRecentAnalysis() {
        recentAnalysisRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        // Setup adapter for recent analysis - placeholder for now
    }    private void setupFeaturedBiases() {
        featuredBiasesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        // Sample data - in production this would come from Firebase/API
        List<CognitiveBias> featuredBiases = getSampleBiases();
        BiasAdapter adapter = new BiasAdapter(featuredBiases);
        featuredBiasesRecyclerView.setAdapter(adapter);
        
        // Show Toast to confirm data is loaded
        Toast.makeText(getContext(), "✅ " + featuredBiases.size() + " cognitive biases loaded and displayed!", Toast.LENGTH_SHORT).show();
    }

    private List<CognitiveBias> getSampleBiases() {
        List<CognitiveBias> biases = new ArrayList<>();
        
        biases.add(new CognitiveBias("1", "Confirmation Bias", 
            "The tendency to search for information that confirms existing beliefs", 
            "Memory", Arrays.asList("Cherry-picking data", "Ignoring contradictory evidence"), 
            "Actively seek out opposing viewpoints", 8, ""));
            
        biases.add(new CognitiveBias("2", "Anchoring Bias", 
            "Over-reliance on the first piece of information encountered", 
            "Decision Making", Arrays.asList("Price negotiations", "Performance evaluations"), 
            "Consider multiple reference points", 7, ""));
            
        biases.add(new CognitiveBias("3", "Availability Heuristic", 
            "Judging probability by how easily examples come to mind", 
            "Probability", Arrays.asList("Fear of flying vs driving", "Media influence on perception"), 
            "Look at actual statistics, not just memorable examples", 6, ""));
        
        return biases;
    }    private void navigateToBiasScanner() {
        try {
            Navigation.findNavController(requireView()).navigate(R.id.nav_bias_scanner);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Navigation error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    
    private void navigateToNewsAnalysis() {
        try {
            Navigation.findNavController(requireView()).navigate(R.id.nav_news_analysis);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Navigation error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
