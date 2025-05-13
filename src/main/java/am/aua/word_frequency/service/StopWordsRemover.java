package am.aua.word_frequency.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StopWordsRemover {
    private Set<String> stopWords;

    public StopWordsRemover() {
        this.stopWords = new HashSet<>(Arrays.asList(
                "եւ", "որ", "մեջ", "թե", "համար", "հետ", "է", "էր", "են",
                "եմ", "ենք", "ու", "իսկ", "կամ", "բայց", "այն", "այս",
                "այդ", "ինչ", "ինչպես", "որպես", "քան", "պետք", "կարող",
                "մի", "ոչ", "արդեն", "նույն", "ամեն", "մինչ", "հետո",
                "նա", "նրանք", "մենք", "դու", "դուք", "նրա", "ինձ", "մեզ",
                "քեզ", "ձեզ", "նրան", "ինքը", "իրեն", "իրենք", "մեր", "ձեր",
                "նրանց", "ինչի", "որը", "որի", "որոնք", "որով", "որից", "և",
                "երբ", "էին", "էլ","չէր","չեն","չէ", "էի", "իր", "սակայն",
                "չէի", "ես", "քան", "մի"
        ));
    }


    public boolean isStopWord(String word) {
        return stopWords.contains(word);
    }

    // Keep the original method for backward compatibility
    public Map<String, Integer> removeStopWords(Map<String, Integer> freqMap) {
        return freqMap.entrySet().stream()
                .filter(entry -> !isStopWord(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}