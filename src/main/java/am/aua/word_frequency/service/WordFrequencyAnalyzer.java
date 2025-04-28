package am.aua.word_frequency.service;

import am.aua.word_frequency.util.ArmenianStemmer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WordFrequencyAnalyzer {
    private final ArmenianStemmer stemmer = new ArmenianStemmer();

    public Map<String, Integer> analyze(String text) {
        Map<String, Integer> freqMap = new HashMap<>();

        for (String word : text.split("\\s+")) {
            if (!word.isEmpty()) {
                String stemmed = stemmer.stem(word);
                // Normalize by removing any remaining non-letter characters
                stemmed = stemmed.replaceAll("[^\\p{IsArmenian}]", "");
                if (!stemmed.isEmpty()) {
                    freqMap.put(stemmed, freqMap.getOrDefault(stemmed, 0) + 1);
                }
            }
        }

        return freqMap;
    }
}