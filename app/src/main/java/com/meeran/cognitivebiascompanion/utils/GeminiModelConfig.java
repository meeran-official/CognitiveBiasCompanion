package com.meeran.cognitivebiascompanion.utils;

public class GeminiModelConfig {
    
    // Model priorities (try in order)
    public static final String[] PREFERRED_MODELS = {
        "gemini-2.5-flash",      // Latest and most intelligent
        "gemini-2.0-flash",      // Backup option  
        "gemini-1.5-flash",      // Stable fallback
        "gemini-pro"             // Last resort
    };
    
    public static final String PRIMARY_MODEL = "gemini-2.5-flash-preview-05-20";
    public static final String FALLBACK_MODEL = "gemini-2.0-flash";
    
    // Rate limit info for user display
    public static String getModelInfo(String model) {
        switch (model) {
            case "gemini-2.5-flash":
                return "🧠 Latest AI • Most Intelligent • Premium Analysis";
            case "gemini-2.0-flash":
                return "⚡ Fast AI • Reliable • Great Performance";
            case "gemini-1.5-flash":
                return "✅ Stable AI • Proven • Good Results";
            case "gemini-pro":
                return "🔧 Basic AI • Simple • Works";
            default:
                return "🤖 AI Model";
        }
    }
    
    public static String getCurrentModel() {
        return PRIMARY_MODEL;
    }
}
