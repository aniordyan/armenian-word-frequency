package am.aua.word_frequency.util;

import java.util.Arrays;
import java.util.List;

public class ArmenianStemmer {
    // Armenian definite articles and suffixes
    private static final List<String> DEFINITE_ARTICLES = Arrays.asList(
            "ը", "ն", "ըն", "են", "ները", "ուն", "ունը", "ան", "անը"
    );

    // Noun declension suffixes
    private static final List<String> DECLENSION_SUFFIXES = Arrays.asList(
            "ի", "ու", "ից", "ով", "ում", "ան", "ին", "ուն", "ույն", "անը", "ունը"
    );

    // Verb conjugation suffixes
    private static final List<String> VERB_SUFFIXES = Arrays.asList(
            "ել", "ալ", "ացնել", "ացնում", "եցնել", "եցնում", "ում", "աց", "եց",
            "անք", "աք", "ին", "ի", "եիր", "եին", "եինք", "եիք", "եին", "են", "եմ"
    );

    public String stem(String word) {
        if (word.length() <= 3) {
            return word; // Don't stem very short words
        }

        String original = word;
        boolean changed = false;

        // Remove definite articles first
        for (String article : DEFINITE_ARTICLES) {
            if (word.endsWith(article)) {
                word = word.substring(0, word.length() - article.length());
                changed = true;
                break;
            }
        }

        // Then try noun declensions
        if (!changed) {
            for (String suffix : DECLENSION_SUFFIXES) {
                if (word.endsWith(suffix) && word.length() > suffix.length() + 1) {
                    word = word.substring(0, word.length() - suffix.length());
                    changed = true;
                    break;
                }
            }
        }

        // Then try verb conjugations
        if (!changed) {
            for (String suffix : VERB_SUFFIXES) {
                if (word.endsWith(suffix) && word.length() > suffix.length() + 1) {
                    word = word.substring(0, word.length() - suffix.length());
                    changed = true;
                    break;
                }
            }
        }

        // If we've stemmed too aggressively, return the original
        if (word.length() < 2 && original.length() > 3) {
            return original;
        }

        return word;
    }
}