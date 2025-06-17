# Security

## API Key Management

API keys are stored in `local.properties` (which is gitignored) and injected via BuildConfig:

```properties
# local.properties
GEMINI_API_KEY=your_key
NEWS_API_KEY=your_key
```

```gradle
// build.gradle.kts
buildConfigField("String", "GEMINI_API_KEY", "\"${getLocalProperty('GEMINI_API_KEY')}\"")
```

```java
// Usage in code
String apiKey = BuildConfig.GEMINI_API_KEY;
```

## What's Secure

- ✅ API keys never in source code
- ✅ Keys gitignored via local.properties
- ✅ HTTPS only for all network calls
- ✅ Input validation on API responses
- ✅ No sensitive user data collected

## What Could Be Better

- Add ProGuard obfuscation for release builds
- Consider using Android Keystore for production
- Add certificate pinning
- Rate limiting for API calls

This is fine for a demo/learning project but you'd want to harden it more for production.
