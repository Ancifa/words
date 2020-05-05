package words.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import words.processor.Processor;

@RestController
public class WordsController {

    @Autowired
    private Processor processor;

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/process")
    public String process(@RequestParam (value = "text") String text) {
        return processor.processWords(text).toString();
    }
}
