package com.meeran.cognitivebiascompanion.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.meeran.cognitivebiascompanion.QuizActivity;
import com.meeran.cognitivebiascompanion.R;

public class LearningFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learning, container, false);
        
        setupClickListeners(view);
        
        return view;
    }
    
    private void setupClickListeners(View view) {
        MaterialButton startQuizButton = view.findViewById(R.id.btn_start_quiz);
        if (startQuizButton != null) {
            startQuizButton.setOnClickListener(v -> startQuiz());
        }
        
        // Add long press help
        if (startQuizButton != null) {
            startQuizButton.setOnLongClickListener(v -> {
                Toast.makeText(getContext(), "🧠 Quiz feature coming soon! This will test your bias detection skills.", Toast.LENGTH_LONG).show();
                return true;
            });
        }
    }
      private void startQuiz() {
        Toast.makeText(getContext(), "🧠 Opening Quiz Activity...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), QuizActivity.class);
        startActivity(intent);
    }
}
