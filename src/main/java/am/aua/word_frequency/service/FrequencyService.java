package am.aua.word_frequency.service;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

@Service
public class FrequencyService {

    private final TextPreprocessor preprocessor;
    private final WordFrequencyAnalyzer analyzer;
    private final StopWordsRemover remover;

    // Constructor - Spring will inject the dependencies
    public FrequencyService(TextPreprocessor preprocessor,
                            WordFrequencyAnalyzer analyzer,
                            StopWordsRemover remover) {
        this.preprocessor = preprocessor;
        this.analyzer = analyzer;
        this.remover = remover;
    }

    public Map<String, Integer> analyze(String text, int wordCount) {
        String cleaned = preprocessor.preprocess(text);
        Map<String, Integer> freqMap = analyzer.analyze(cleaned);
        Map<String, Integer> filtered = remover.removeStopWords(freqMap);

        return filtered.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(wordCount)  // Use the parameter instead of hardcoded 10
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}