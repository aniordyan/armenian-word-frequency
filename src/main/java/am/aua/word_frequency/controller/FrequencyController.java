package am.aua.word_frequency.controller;

import am.aua.word_frequency.service.FrequencyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class FrequencyController {

    private final FrequencyService frequencyService;

    // Spring will automatically inject the service
    public FrequencyController(FrequencyService frequencyService) {
        this.frequencyService = frequencyService;
    }

    @GetMapping("/")
    public String showForm() {
        return "upload"; // Will look for upload.html in templates
    }

    @PostMapping("/analyze")
    public String analyzeText(
            @RequestParam String text,
            @RequestParam(defaultValue = "10") int wordCount,
            Model model) {

        Map<String, Integer> frequencies = frequencyService.analyze(text, wordCount);
        model.addAttribute("frequencies", frequencies);
        model.addAttribute("selectedCount", wordCount);
        return "results";
    }
}