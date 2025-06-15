# 🧠 Cognitive Bias Companion - Beast Mode 🚀

Transform your simple app into a comprehensive AI-powered cognitive bias detection and learning platform!

## 🌟 What Makes This App a Beast?

### Core Features
- **🔍 AI-Powered Bias Scanner**: Analyze any text for cognitive biases using OpenAI GPT
- **📰 Real-time News Analysis**: Detect bias in news articles with NewsAPI integration
- **📚 Interactive Learning Hub**: Master 100+ cognitive biases with personalized content
- **📊 Advanced Analytics**: Track your bias awareness progress with beautiful charts
- **🧭 Smart Navigation**: Modern Material Design with bottom navigation and drawer

### Beast-Level Integrations

#### 1. **Google Gemini API** 🤖 (RECOMMENDED!)
- **What it does**: Analyzes text for cognitive biases using Gemini Pro
- **Free Tier**: Completely FREE, no credit card needed!
- **Why it's better**: Faster, more reliable, better at bias detection
- **Setup**: 
  1. Get API key from [Google AI Studio](https://makersuite.google.com/app/apikey)
  2. Add `GEMINI_API_KEY="your_key"` to `local.properties`

#### 2. **NewsAPI** 📰
- **What it does**: Fetches latest news for bias analysis
- **Free Tier**: 1,000 requests/day
- **Setup**:
  1. Get API key from [NewsAPI.org](https://newsapi.org)
  2. Add `NEWS_API_KEY="your_key"` to `local.properties`

#### 3. **Firebase** 🔥 (Ready for integration)
- **What it enables**: User auth, cloud storage, analytics
- **Free Tier**: Very generous limits
- **Next Steps**: Add `google-services.json` file

#### 4. **Material Design 3** 🎨
- Modern UI with dynamic theming
- Smooth animations and transitions
- Accessibility-first design

## 🏗️ Architecture

```
📁 app/
├── 📁 models/           # Data models (CognitiveBias, BiasAnalysis, NewsArticle)
├── 📁 api/             # Network services (OpenAI, NewsAPI, ApiClient)
├── 📁 fragments/       # UI fragments (Home, Scanner, News, Learning, Analytics)
├── 📁 adapters/        # RecyclerView adapters (coming soon)
├── 📁 utils/           # Helper classes (coming soon)
└── MainActivity.java   # Navigation controller
```

## 🚀 Quick Start

### 1. Secure API Key Setup ⚠️ IMPORTANT!

**DON'T put API keys in code!** We use `local.properties` for security:

1. **Get Your API Keys:**
   - **Gemini API (FREE!)**: Get from [Google AI Studio](https://makersuite.google.com/app/apikey)
   - **NewsAPI**: Get from [NewsAPI.org](https://newsapi.org/register)

2. **Add to `local.properties` (this file is gitignored):**
```properties
# Add these lines to your local.properties file:
NEWS_API_KEY="your_actual_news_api_key_here"
GEMINI_API_KEY="your_actual_gemini_api_key_here"
```

3. **Why Gemini is Better:**
   - ✅ **Completely FREE** (no credit card required)
   - ✅ **No signup complexity** 
   - ✅ **Better multilingual support**
   - ✅ **Faster response times**
   - ✅ **More reliable for text analysis**

### 2. Build & Run
```bash
# In Android Studio:
# 1. Sync Project with Gradle Files
# 2. Build -> Clean Project
# 3. Build -> Rebuild Project  
# 4. Run the app
```

### 3. Test Features
- **Home**: See daily bias and quick actions
- **Scanner**: Paste text and analyze for biases
- **News**: Browse latest news (requires NewsAPI key)
- **Learning**: Interactive bias encyclopedia (coming soon)
- **Analytics**: View your progress and stats

## 📱 Screenshots Preview

```
🏠 Home Screen              🔍 Bias Scanner           📰 News Analysis
┌─────────────────┐        ┌─────────────────┐       ┌─────────────────┐
│ Welcome Card    │        │ Text Input      │       │ Latest Articles │
│ Daily Bias      │   →    │ Analyze Button  │   →   │ Bias Scores     │
│ Quick Actions   │        │ Results List    │       │ Read More       │
└─────────────────┘        └─────────────────┘       └─────────────────┘
```

## 🎯 Planned Beast Features

### Phase 2 (Coming Soon)
- **🤖 Smart Notifications**: Daily bias tips and reminders
- **👥 Social Features**: Share analyses with friends
- **🏆 Gamification**: Achievement system and leaderboards
- **📱 Widget Support**: Home screen bias detector
- **🔊 Voice Analysis**: Analyze spoken content
- **📷 Image OCR**: Extract and analyze text from images

### Phase 3 (Future)
- **🧠 Machine Learning**: Custom bias detection models
- **📈 Advanced Analytics**: Bias pattern recognition
- **🌍 Multi-language**: Support for multiple languages
- **💬 Chat Bot**: Interactive bias counselor
- **📖 Course System**: Structured learning paths

## 🔧 Technical Details

### Dependencies Added
```gradle
// Navigation & UI
implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
implementation("androidx.recyclerview:recyclerview:1.3.2")

// Networking
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")

// Charts & Visualization
implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

// Image Loading
implementation("com.github.bumptech.glide:glide:4.16.0")

// Firebase (Ready)
implementation("com.google.firebase:firebase-bom:32.7.0")
```

### Build Features Enabled
- **ViewBinding**: Type-safe view references
- **Material Design 3**: Latest Material Design components
- **Edge-to-Edge**: Modern full-screen experience

## 🎨 UI/UX Highlights

- **Consistent Design**: Material Design 3 throughout
- **Intuitive Navigation**: Bottom nav + navigation drawer
- **Progressive Disclosure**: Complex features made simple
- **Accessibility**: Screen reader support, proper contrast
- **Responsive**: Works on tablets and phones

## 🔐 Security & Privacy

- **API Keys**: Store securely (consider using BuildConfig)
- **User Data**: Firebase security rules (when implemented)
- **Network**: HTTPS only for all API calls
- **Permissions**: Minimal required permissions

## 🚀 Performance Optimizations

- **Lazy Loading**: RecyclerViews with view recycling
- **Image Caching**: Glide for efficient image loading
- **Background Processing**: Retrofit with proper threading
- **Memory Management**: Proper lifecycle handling

## 🤝 Contributing

This beast app is ready for:
- **Feature additions**: New bias types, analysis methods
- **UI improvements**: Better animations, themes
- **Performance**: Caching, offline support
- **Testing**: Unit tests, UI tests

## 🏆 What Makes This a "Beast" App?

1. **Multiple AI Integrations**: OpenAI + NewsAPI + future ML models
2. **Comprehensive Feature Set**: Analysis + Learning + Analytics + Social
3. **Professional Architecture**: Clean, scalable, maintainable code
4. **Modern Android**: Latest libraries, Material Design 3, best practices
5. **Real-world Impact**: Actually helps people make better decisions
6. **Growth Potential**: Endless possibilities for new features

## 📞 Support & Feedback

- **Issues**: Open GitHub issues for bugs
- **Features**: Submit feature requests
- **Discussions**: Join the cognitive bias discussion

---

**Transform your simple app into a cognitive bias detection powerhouse! 🧠⚡**

*Ready to help users overcome their biases and make better decisions!*
