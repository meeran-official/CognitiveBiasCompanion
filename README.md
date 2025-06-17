# Cognitive Bias Companion

An Android app that uses AI to spot cognitive biases in text. Think of it as a BS detector powered by Google's Gemini AI.

## What it does

Drop any text into the app and it'll tell you what cognitive biases might be lurking in there - confirmation bias, anchoring, availability heuristic, you name it. It also pulls in live news and lets you analyze articles for bias.

There's also a quiz if you want to test your knowledge about these mental traps we all fall into.

## Tech Stack

- **Android**: Native with Material Design 3
- **AI**: Google Gemini API for bias detection  
- **News**: NewsAPI for live articles
- **Networking**: Retrofit + OkHttp
- **UI**: ViewBinding, Navigation Component, RecyclerView

## Quick Start

You'll need API keys (both are free):

1. Gemini: [makersuite.google.com](https://makersuite.google.com/app/apikey)
2. NewsAPI: [newsapi.org](https://newsapi.org/register)

Drop them in `local.properties`:
```properties
GEMINI_API_KEY=your_key
NEWS_API_KEY=your_key
```

Build and run. That's it.

## Project Structure

```
app/
├── models/           # Data models (BiasResult, NewsArticle, QuizQuestion)
├── api/             # Retrofit services + API clients
├── fragments/       # Main screens (home, scanner, news, quiz, analytics)
├── adapters/        # RecyclerView adapters
├── activities/      # MainActivity + QuizActivity
└── utils/           # Helper classes
```

## How it works

**Text Scanner**: Paste text, app sends it to Gemini with a structured prompt, gets back JSON with bias types and confidence scores. Results show up in nice cards with progress bars.

**News Feed**: Fetches articles from NewsAPI, each has an "Analyze" button that runs the same bias detection on the article content.

**Quiz**: Hardcoded questions about cognitive biases. Multiple choice with explanations. Tracks your score.

## Key Implementation Details

### API Integration
Gemini API uses a custom prompt that asks for structured JSON responses. Looks like this:
```
"Analyze this text for cognitive biases. Return JSON with bias_type, confidence, explanation, snippet fields."
```

Error handling includes retries and fallback to sample data when APIs are down.

### UI Architecture  
Bottom nav with 5 tabs. ViewBinding everywhere. Material Design 3 components with custom styling for bias result cards.

Navigation Component handles the fragment switching. No activities except MainActivity and QuizActivity.

### Security
API keys come from BuildConfig at runtime, which pulls from local.properties during build. Keeps sensitive stuff out of git.

```gradle
buildConfigField("String", "GEMINI_API_KEY", "\"${getLocalProperty('GEMINI_API_KEY')}\"")
```

## Dependencies

```gradle
// Core Android
implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
implementation("com.google.android.material:material:1.11.0")

// Networking  
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
```

## Current State

Everything works. The bias detection is surprisingly good - Gemini does a decent job identifying different types of biases and explaining them.

**What's implemented:**
- Text analysis with confidence scores
- Live news feed with per-article analysis
- Educational quiz with scoring
- Material Design 3 UI
- Proper error handling

**What's missing:**
- Analytics tab is just a placeholder
- No local storage (everything's ephemeral)
- Quiz questions are hardcoded
- English only

## If you want to extend this

Some ideas:
- Add SQLite to save analysis history
- More quiz categories or user-generated questions  
- Export results to PDF/text
- Offline mode with cached content
- Support for other languages
- Custom bias definitions

The codebase is pretty clean so adding features shouldn't be too painful.

## Notes

This was built as a learning project but turned out pretty solid. The AI integration works well and the UI feels polished.

Gemini API is free and doesn't require a credit card, which is nice. NewsAPI has a generous free tier too.

The app handles network failures gracefully and includes sample data for testing without API keys.
