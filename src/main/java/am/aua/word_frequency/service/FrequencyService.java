package am.aua.word_frequency.service;
import am.aua.word_frequency.util.ArmenianStemmer;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FrequencyService {

    private final TextPreprocessor preprocessor;
    private final StopWordsRemover remover;
    private final ArmenianStemmer stemmer;

    public FrequencyService(TextPreprocessor preprocessor,
                            StopWordsRemover remover) {
        this.preprocessor = preprocessor;
        this.remover = remover;
        this.stemmer = new ArmenianStemmer();
    }

    public Map<String, Integer> analyze(String text, int wordCount) {
        // 1. Preprocess
        String cleaned = preprocessor.preprocess(text);

        // 2. Split and filter stop words first
        List<String> words = Arrays.stream(cleaned.split("\\s+"))
                .filter(word -> !word.isEmpty())
                .filter(word -> !remover.isStopWord(word)) // Immediate stop word removal
                .collect(Collectors.toList());

        // 3. Stem remaining words and count
        Map<String, Integer> freqMap = new HashMap<>();
        for (String word : words) {
            String stemmed = stemmer.stem(word);
            stemmed = stemmed.replaceAll("[^\\p{IsArmenian}]", "");
            if (!stemmed.isEmpty()) {
                freqMap.put(stemmed, freqMap.getOrDefault(stemmed, 0) + 1);
            }
        }

        // 4. Sort and limit results
        return freqMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(wordCount)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}