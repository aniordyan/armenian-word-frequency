package am.aua.word_frequency.service;

import org.springframework.stereotype.Service;

@Service

public class TextPreprocessor {
    public String preprocess(String text) {
        // Replace Armenian punctuation with spaces
        text = text.replaceAll("[։,»«֊]", " ");

        // Remove all punctuation except apostrophes (for contractions)
        text = text.replaceAll("[^\\p{IsArmenian}\\p{IsAlphabetic}\\s']", " ");

        // Normalize whitespace
        text = text.replaceAll("\\s+", " ").trim();

        return text.toLowerCase();
    }
}