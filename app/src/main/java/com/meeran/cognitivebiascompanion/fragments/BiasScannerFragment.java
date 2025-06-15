package com.meeran.cognitivebiascompanion.fragments;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.meeran.cognitivebiascompanion.BuildConfig;
import com.meeran.cognitivebiascompanion.R;
import com.meeran.cognitivebiascompanion.adapters.BiasResultAdapter;
import com.meeran.cognitivebiascompanion.api.ApiClient;
import com.meeran.cognitivebiascompanion.api.GeminiApiService;
import com.meeran.cognitivebiascompanion.models.BiasAnalysis;
import com.meeran.cognitivebiascompanion.models.BiasDetectionResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BiasScannerFragment extends Fragment {
    private static final String TAG = "BiasScannerFragment";    private TextInputEditText inputText;
    private MaterialButton analyzeButton, pasteButton;
    private RecyclerView analysisResultsRecyclerView;
    private TextView analysisResultsText;
    private View progressIndicator, resultsCard;
    private BiasResultAdapter biasResultAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bias_scanner, container, false);
        
        initViews(view);
        setupClickListeners();
        setupRecyclerView();
        
        return view;
    }    private void initViews(View view) {
        inputText = view.findViewById(R.id.input_text);
        analyzeButton = view.findViewById(R.id.analyze_button);
        pasteButton = view.findViewById(R.id.paste_button);
        analysisResultsRecyclerView = view.findViewById(R.id.analysis_results_recyclerview);
        analysisResultsText = view.findViewById(R.id.analysis_results_text);
        progressIndicator = view.findViewById(R.id.progress_indicator);
        resultsCard = view.findViewById(R.id.results_card);
    }private void setupClickListeners() {
        analyzeButton.setOnClickListener(v -> analyzeText());
        pasteButton.setOnClickListener(v -> pasteFromClipboard());
        
        // Add test functionality
        analyzeButton.setOnLongClickListener(v -> {
            Toast.makeText(getContext(), "🔍 Analyze button working! Enter text and tap to analyze.", Toast.LENGTH_SHORT).show();
            return true;
        });
        
        pasteButton.setOnLongClickListener(v -> {
            Toast.makeText(getContext(), "📋 Paste button working! Tap to paste from clipboard.", Toast.LENGTH_SHORT).show();
            return true;
        });
    }    private void setupRecyclerView() {
        analysisResultsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // Adapter will be set when we have results to display
    }private void analyzeText() {
        String text = inputText.getText().toString().trim();
        
        if (TextUtils.isEmpty(text)) {
            inputText.setError("Please enter some text to analyze");
            return;
        }

        // Show user that analysis is starting
        Toast.makeText(getContext(), "🤖 Starting AI analysis with Gemini...", Toast.LENGTH_SHORT).show();
        showProgress(true);
        
        // Create Gemini request for bias analysis
        String prompt = createBiasAnalysisPrompt(text);
        
        GeminiApiService.GeminiRequest.Content.Part part = 
            new GeminiApiService.GeminiRequest.Content.Part(prompt);
        GeminiApiService.GeminiRequest.Content content = 
            new GeminiApiService.GeminiRequest.Content(new GeminiApiService.GeminiRequest.Content.Part[]{part});
        GeminiApiService.GeminiRequest request = 
            new GeminiApiService.GeminiRequest(new GeminiApiService.GeminiRequest.Content[]{content});

        String apiKey = BuildConfig.GEMINI_API_KEY;
        if (TextUtils.isEmpty(apiKey)) {
            showProgress(false);
            Toast.makeText(getContext(), "❌ Please configure your Gemini API key in local.properties", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(getContext(), "📡 Sending request to Gemini API...", Toast.LENGTH_SHORT).show();

        ApiClient.getGeminiService().analyzeBias(apiKey, request).enqueue(new Callback<GeminiApiService.GeminiResponse>() {
            @Override
            public void onResponse(Call<GeminiApiService.GeminiResponse> call, Response<GeminiApiService.GeminiResponse> response) {
                showProgress(false);
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(), "✅ Received response from Gemini!", Toast.LENGTH_SHORT).show();
                    handleGeminiAnalysisResponse(response.body(), text);
                } else {
                    Toast.makeText(getContext(), "❌ Analysis failed. Response code: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GeminiApiService.GeminiResponse> call, Throwable t) {
                showProgress(false);
                Toast.makeText(getContext(), "❌ Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }private String createBiasAnalysisPrompt(String text) {
        return "You are an expert cognitive bias analyst. Analyze the following text and identify cognitive biases present.\n\n" +
               "Please respond in this JSON format:\n" +
               "{\n" +
               "  \"biases_detected\": [\n" +
               "    {\n" +
               "      \"name\": \"Confirmation Bias\",\n" +
               "      \"confidence\": 0.85,\n" +
               "      \"explanation\": \"The text shows selective use of evidence...\",\n" +
               "      \"text_snippet\": \"relevant quote from text\"\n" +
               "    }\n" +
               "  ],\n" +
               "  \"overall_bias_score\": 0.7,\n" +
               "  \"summary\": \"Overall assessment of bias in the text\",\n" +
               "  \"recommendations\": \"How to improve critical thinking here\"\n" +
               "}\n\n" +
               "Focus on these bias types: Confirmation Bias, Anchoring Bias, Availability Heuristic, " +
               "Survivorship Bias, Selection Bias, Appeal to Authority, False Dichotomy, Strawman Arguments.\n\n" +
               "Text to analyze:\n\"" + text + "\"";
    }    private void handleGeminiAnalysisResponse(GeminiApiService.GeminiResponse response, String originalText) {
        if (response.getCandidates() != null && response.getCandidates().length > 0) {
            String analysisResult = response.getCandidates()[0].getContent().getParts()[0].getText();
            
            Log.d(TAG, "Analysis result received: " + analysisResult.substring(0, Math.min(200, analysisResult.length())) + "...");
            
            // Parse the JSON response and create BiasAnalysis object
            Toast.makeText(getContext(), "🎯 Analysis complete! Found insights.", Toast.LENGTH_SHORT).show();
            
            // Show the analysis result
            showAnalysisResult(analysisResult, originalText);
        } else {
            Toast.makeText(getContext(), "❌ No analysis results received", Toast.LENGTH_SHORT).show();
        }
    }    private void showAnalysisResult(String analysisResult, String originalText) {
        try {
            // Clean and parse the JSON response
            String cleanedJson = cleanJsonResponse(analysisResult);
            Gson gson = new Gson();
            BiasDetectionResult result = gson.fromJson(cleanedJson, BiasDetectionResult.class);
            
            if (result != null && result.getBiasesDetected() != null && !result.getBiasesDetected().isEmpty()) {
                // Show beautiful parsed results
                displayParsedResults(result, originalText);
            } else {
                // Fallback to formatted raw result if parsing fails
                displayRawResults(analysisResult, originalText);
            }
        } catch (JsonSyntaxException e) {
            Log.e(TAG, "JSON parsing failed", e);
            // Fallback to formatted raw result
            displayRawResults(analysisResult, originalText);
        }
        
        // Clear input for next analysis  
        inputText.setText("");
        
        // Show success message
        Toast.makeText(getContext(), "✅ Analysis Complete! Check results below.", Toast.LENGTH_LONG).show();
    }

    private String cleanJsonResponse(String rawResponse) {
        // Remove code block markers and clean up the response
        String cleaned = rawResponse.replace("```json", "").replace("```", "").trim();
        
        // Find JSON start and end
        int jsonStart = cleaned.indexOf("{");
        int jsonEnd = cleaned.lastIndexOf("}");
        
        if (jsonStart != -1 && jsonEnd != -1 && jsonEnd > jsonStart) {
            cleaned = cleaned.substring(jsonStart, jsonEnd + 1);
        }
        
        return cleaned;
    }

    private void displayParsedResults(BiasDetectionResult result, String originalText) {
        // Show the results in beautiful cards
        resultsCard.setVisibility(View.GONE); // Hide raw text display
        analysisResultsRecyclerView.setVisibility(View.VISIBLE);
        
        // Create summary text for the header
        String summaryText = String.format("📊 Overall Bias Score: %.0f%%\n\n", result.getOverallBiasScore() * 100);
        if (result.getSummary() != null && !result.getSummary().trim().isEmpty()) {
            summaryText += "📝 Summary: " + result.getSummary() + "\n\n";
        }
        if (result.getRecommendations() != null && !result.getRecommendations().trim().isEmpty()) {
            summaryText += "💡 Recommendations: " + result.getRecommendations();
        }
        
        analysisResultsText.setText(summaryText);
        resultsCard.setVisibility(View.VISIBLE);
        
        // Setup adapter with detected biases
        if (biasResultAdapter == null) {
            biasResultAdapter = new BiasResultAdapter(result.getBiasesDetected());
            analysisResultsRecyclerView.setAdapter(biasResultAdapter);
        } else {
            biasResultAdapter.updateBiases(result.getBiasesDetected());
        }
        
        Log.d(TAG, "Displayed " + result.getBiasesDetected().size() + " detected biases");
    }

    private void displayRawResults(String analysisResult, String originalText) {
        // Fallback to showing formatted raw result
        resultsCard.setVisibility(View.VISIBLE);
        analysisResultsRecyclerView.setVisibility(View.GONE);
        
        String formattedResult = formatAnalysisResult(analysisResult, originalText);
        analysisResultsText.setText(formattedResult);
        
        Log.d(TAG, "Displayed raw analysis result");
    }

    private String formatAnalysisResult(String rawResult, String originalText) {
        // Try to parse JSON and format nicely, or show raw result if parsing fails
        try {
            // Simple formatting for now - in production you'd parse the JSON properly
            String formatted = "📝 Original Text: " + originalText.substring(0, Math.min(100, originalText.length())) + "...\n\n";
            formatted += "🧠 AI Analysis:\n" + rawResult;
            
            // Clean up common JSON artifacts
            formatted = formatted.replace("```json", "").replace("```", "");
            formatted = formatted.replace("\\n", "\n");
            
            return formatted;
        } catch (Exception e) {
            Log.e(TAG, "Error formatting result", e);
            return "📝 Analysis Result:\n\n" + rawResult;
        }
    }

    private void pasteFromClipboard() {
        ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard.hasPrimaryClip() && clipboard.getPrimaryClip().getItemCount() > 0) {
            CharSequence pasteData = clipboard.getPrimaryClip().getItemAt(0).getText();
            if (pasteData != null) {
                inputText.setText(pasteData.toString());
            }
        } else {
            Toast.makeText(getContext(), "Clipboard is empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void showProgress(boolean show) {
        progressIndicator.setVisibility(show ? View.VISIBLE : View.GONE);
        analyzeButton.setEnabled(!show);
    }
}
