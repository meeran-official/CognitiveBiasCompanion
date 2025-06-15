package com.meeran.cognitivebiascompanion;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.meeran.cognitivebiascompanion.models.QuizQuestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView questionText, scoreText;
    private MaterialButton option1Button, option2Button, option3Button, option4Button, nextButton;
    private MaterialCardView questionCard;
    private List<QuizQuestion> quizQuestions;
    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_new);

        setupToolbar();
        initViews();
        setupQuizQuestions();
        displayCurrentQuestion();
    }

    private void initViews() {
        questionText = findViewById(R.id.question_text);
        scoreText = findViewById(R.id.score_text);
        option1Button = findViewById(R.id.option1_button);
        option2Button = findViewById(R.id.option2_button);
        option3Button = findViewById(R.id.option3_button);
        option4Button = findViewById(R.id.option4_button);
        nextButton = findViewById(R.id.next_button);
        questionCard = findViewById(R.id.question_card);

        // Setup click listeners
        option1Button.setOnClickListener(v -> selectAnswer(0));
        option2Button.setOnClickListener(v -> selectAnswer(1));
        option3Button.setOnClickListener(v -> selectAnswer(2));
        option4Button.setOnClickListener(v -> selectAnswer(3));
        nextButton.setOnClickListener(v -> nextQuestion());
    }

    private void setupQuizQuestions() {
        quizQuestions = new ArrayList<>();
        
        quizQuestions.add(new QuizQuestion(
            "What is confirmation bias?",
            Arrays.asList(
                "The tendency to search for information that confirms our existing beliefs",
                "The tendency to remember negative events more clearly",
                "The tendency to overestimate our abilities",
                "The tendency to follow the crowd"
            ),
            0,
            "Confirmation bias is the tendency to search for, interpret, and recall information in a way that confirms our pre-existing beliefs.",
            "Confirmation Bias"
        ));

        quizQuestions.add(new QuizQuestion(
            "Which scenario best demonstrates anchoring bias?",
            Arrays.asList(
                "Buying expensive items because they're on sale",
                "Being heavily influenced by the first price you see when negotiating",
                "Preferring familiar brands over unknown ones",
                "Following investment advice from friends"
            ),
            1,
            "Anchoring bias occurs when we rely too heavily on the first piece of information encountered (the 'anchor') when making decisions.",
            "Anchoring Bias"
        ));

        quizQuestions.add(new QuizQuestion(
            "What is the availability heuristic?",
            Arrays.asList(
                "Making decisions based on easily recalled examples",
                "Choosing the most expensive option available",
                "Preferring things that are readily available",
                "Using shortcuts to solve problems quickly"
            ),
            0,
            "The availability heuristic is judging probability by how easily examples come to mind, often leading to overestimating dramatic but rare events.",
            "Availability Heuristic"
        ));

        quizQuestions.add(new QuizQuestion(
            "Survivorship bias occurs when we:",
            Arrays.asList(
                "Focus only on successful examples while ignoring failures",
                "Believe we're more likely to survive dangerous situations",
                "Choose survival over other priorities",
                "Learn from those who survived difficult experiences"
            ),
            0,
            "Survivorship bias happens when we focus on successful people or things while overlooking those that didn't make it, leading to false conclusions.",
            "Survivorship Bias"
        ));

        quizQuestions.add(new QuizQuestion(
            "Which best describes the bandwagon effect?",
            Arrays.asList(
                "Preferring to travel by bus rather than car",
                "Following popular trends without critical thinking",
                "Supporting the winning team in sports",
                "Joining social movements for personal gain"
            ),
            1,
            "The bandwagon effect is the tendency to adopt beliefs or behaviors because many other people have adopted them, regardless of evidence.",
            "Bandwagon Effect"
        ));
    }

    private void displayCurrentQuestion() {
        if (currentQuestionIndex < quizQuestions.size()) {
            QuizQuestion question = quizQuestions.get(currentQuestionIndex);
            
            questionText.setText(question.getQuestion());
            scoreText.setText("Score: " + score + "/" + quizQuestions.size() + " | Question " + (currentQuestionIndex + 1) + "/" + quizQuestions.size());
            
            option1Button.setText(question.getOptions().get(0));
            option2Button.setText(question.getOptions().get(1));
            option3Button.setText(question.getOptions().get(2));
            option4Button.setText(question.getOptions().get(3));
            
            // Reset button states
            resetButtonStates();
            nextButton.setVisibility(View.GONE);
        } else {
            showQuizComplete();
        }
    }

    private void selectAnswer(int selectedIndex) {
        QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);
        boolean isCorrect = selectedIndex == currentQuestion.getCorrectAnswerIndex();
        
        if (isCorrect) {
            score++;
            highlightCorrectAnswer(selectedIndex);
            Toast.makeText(this, "✅ Correct!", Toast.LENGTH_SHORT).show();
        } else {
            highlightWrongAnswer(selectedIndex);
            highlightCorrectAnswer(currentQuestion.getCorrectAnswerIndex());
            Toast.makeText(this, "❌ Incorrect. " + currentQuestion.getExplanation(), Toast.LENGTH_LONG).show();
        }
        
        disableAllButtons();
        nextButton.setVisibility(View.VISIBLE);
        scoreText.setText("Score: " + score + "/" + quizQuestions.size() + " | Question " + (currentQuestionIndex + 1) + "/" + quizQuestions.size());
    }

    private void nextQuestion() {
        currentQuestionIndex++;
        displayCurrentQuestion();
    }

    private void resetButtonStates() {
        MaterialButton[] buttons = {option1Button, option2Button, option3Button, option4Button};
        for (MaterialButton button : buttons) {
            button.setEnabled(true);
            button.setBackgroundColor(getColor(android.R.color.transparent));
        }
    }

    private void highlightCorrectAnswer(int index) {
        MaterialButton[] buttons = {option1Button, option2Button, option3Button, option4Button};
        buttons[index].setBackgroundColor(getColor(R.color.bias_low)); // Green
    }

    private void highlightWrongAnswer(int index) {
        MaterialButton[] buttons = {option1Button, option2Button, option3Button, option4Button};
        buttons[index].setBackgroundColor(getColor(R.color.bias_high)); // Red
    }

    private void disableAllButtons() {
        option1Button.setEnabled(false);
        option2Button.setEnabled(false);
        option3Button.setEnabled(false);
        option4Button.setEnabled(false);
    }

    private void showQuizComplete() {
        double percentage = (double) score / quizQuestions.size() * 100;
        String resultMessage = "🎉 Quiz Complete!\n\n" +
                              "Final Score: " + score + "/" + quizQuestions.size() + " (" + String.format("%.0f", percentage) + "%)\n\n";
        
        if (percentage >= 80) {
            resultMessage += "🌟 Excellent! You have a great understanding of cognitive biases!";
        } else if (percentage >= 60) {
            resultMessage += "👍 Good job! Keep learning about cognitive biases.";
        } else {
            resultMessage += "📚 Keep studying! Cognitive biases are tricky but important to understand.";
        }

        questionText.setText(resultMessage);
        questionCard.setVisibility(View.VISIBLE);
        
        // Hide option buttons and show restart option
        option1Button.setVisibility(View.GONE);
        option2Button.setVisibility(View.GONE);
        option3Button.setVisibility(View.GONE);
        option4Button.setVisibility(View.GONE);
        
        nextButton.setText("Back to Learning");
        nextButton.setVisibility(View.VISIBLE);
        nextButton.setOnClickListener(v -> finish());
    }    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Bias Detection Quiz");
        }
    }

    @Override    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
