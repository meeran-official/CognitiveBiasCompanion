# Cognitive Bias Companion

An Android app that helps you identify cognitive biases in text using AI. Built with Google Gemini and NewsAPI integration.

## Features

- **Text Analysis**: Paste any text and get AI-powered bias detection with confidence scores
- **News Analysis**: Browse current news articles and analyze them for potential biases
- **Educational Quiz**: Interactive quiz to test your knowledge of cognitive biases
- **Modern Interface**: Clean Material Design 3 UI with smooth navigation

## What it does

The app uses Google's Gemini AI to analyze text for common cognitive biases like confirmation bias, anchoring bias, availability heuristic, and others. It provides structured results with explanations and confidence levels.

For news analysis, it fetches articles from NewsAPI and lets you analyze individual articles to understand potential bias in reporting.

## Setup

You'll need two free API keys:

1. **Gemini API**: Get one from [Google AI Studio](https://makersuite.google.com/app/apikey)
2. **NewsAPI**: Sign up at [newsapi.org](https://newsapi.org/register)

Add them to your `local.properties` file:
```
GEMINI_API_KEY=your_key_here
NEWS_API_KEY=your_key_here
```

Then build and run the project in Android Studio.

## Project Structure

```
app/
├── models/           # Data classes for bias results, news articles, quiz questions
├── api/             # Retrofit services for Gemini and NewsAPI
├── fragments/       # Main app screens (home, scanner, news, learning, analytics)
├── adapters/        # RecyclerView adapters for displaying results
├── activities/      # MainActivity and QuizActivity
└── res/             # Layouts and resources
```

## How it works

### Bias Scanner
Enter or paste text into the scanner. The app sends it to Google Gemini with a structured prompt asking for bias analysis. Results come back as JSON with bias types, confidence percentages, and explanations.

### News Analysis  
The app fetches recent articles from NewsAPI and displays them in a feed. Tap "Analyze Bias" on any article to run it through the same Gemini analysis.

### Quiz
A simple multiple-choice quiz about different cognitive biases. Questions cover common biases with explanations for each answer.

## Dependencies

- Retrofit for networking
- Material Design Components  
- Android Navigation Component
- ViewBinding for layouts

## Building

Standard Android Studio project. Make sure you have:
- Android SDK 24+
- Valid API keys in local.properties
- Internet permission (already included)

The app handles network errors gracefully and includes fallback content when APIs are unavailable.

## Screenshots

The app has five main sections accessible via bottom navigation:

- **Home**: Welcome screen with daily bias information and quick actions
- **Scanner**: Text input area with paste functionality and analysis results
- **News**: Feed of current articles with individual bias analysis options  
- **Learning**: Educational quiz with immediate feedback and scoring
- **Analytics**: Progress tracking (placeholder for now)

## Technical notes

### API Integration
The Gemini integration uses a structured prompt to get consistent JSON responses. Error handling includes retry logic and fallback to sample data when the API is unavailable.

NewsAPI responses are parsed into article objects with proper null checking since some fields can be missing.

### UI Implementation  
Built with Material Design 3 components. The bias results use custom card layouts with progress indicators to show confidence levels visually.

Navigation uses the Android Navigation Component with a bottom navigation view. ViewBinding is enabled throughout for type-safe view references.

### Security
API keys are loaded from BuildConfig at runtime, which gets them from local.properties during build. This keeps sensitive data out of version control.

## Known limitations

- Quiz questions are currently hardcoded (could be moved to a database)
- Analytics tab is a placeholder  
- No offline mode (requires internet for all features)
- Limited to English text analysis

## Future improvements

Some ideas for extending the app:
- Save analysis history locally
- Export analysis results
- More quiz categories  
- Custom bias detection rules
- Social sharing of results

## License

This project is for educational purposes. API usage is subject to the terms of Google AI and NewsAPI respectively.
