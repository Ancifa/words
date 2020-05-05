package words.processor;

import lombok.Getter;

import java.util.*;

@Getter
public class Processor {
    private final String EMPTY = "";
    private final String SPACE = " ";
    private final List<String> exclusions = Arrays.asList(
            "a", "an", "and", "are", "as", "at", "am", "all",
            "be", "been", "by",
            "can", "could",
            "do", "did", "done", "dont",
            "for", "from",
            "give",
            "have", "has", "had", "he", "his", "her",
            "i", "if", "in", "is", "it",
            "me", "mine", "may", "might",
            "no", "not",
            "of", "off", "out", "or", "our", "ours", "on",
            "she", "shall", "should",
            "to", "they", "their", "theirs", "this", "that", "these", "those", "the", "thank", "thanks",
            "us", "up",
            "what", "with", "without", "when", "where", "will", "would",
            "yes", "you", "your", "yours",
            EMPTY, SPACE
    );
    private int counter;

    private Sentence sentence;

    public Map<Integer, String> processSentences(String text) {
        Map<Integer, String> sentences = getSentences(text);
        counter = sentences.size();

        return sentences;
    }

    public List<Word> processWords(String text) {
        Map<Integer, String> sentences = getSentences(text);
        List<Word> words = getWords(sentences);
//        removeExclusions(words);
        counter = words.size();

        return words;
    }

    private Map<Integer, String> getSentences(String text) {
        String[] sentences = text.split("[.!?\\n]");

        Map<Integer, String> sentencesMap = new HashMap<>();
        int key = 1;
        for (String sentence : sentences) {
            if (EMPTY.equals(sentence)) {
                continue;
            }
            sentencesMap.put(key++, sentence);
        }

        sentence = new Sentence().setSentencesMap(sentencesMap);

        return sentencesMap;
    }

    private List<Word> getWords(Map<Integer, String> sentences) {
        List<Word> words = new ArrayList<>();
        for (Map.Entry<Integer, String> sentence : sentences.entrySet()) {
            String[] wordsArray = sentence.getValue().split("[ /]");
            List<String> newWordsArray = removeSymbols(wordsArray);
            newWordsArray.forEach(item -> {
                Word word = new Word().setItem(item).setSentenceNumber(sentence.getKey()).setQuantity(1);
                words.add(word);
            });
        }

        return words;
    }

    private List<String> removeSymbols(String[] wordsArray) {
        List<String> newWordsList = new ArrayList<>();
        for (String s : wordsArray) {
            if (SPACE.equals(s) || EMPTY.equals(s)) {
                continue;
            }
            String word = s.replaceAll("[^a-zA-Z'’/-]", "");
            String newWord = word.toLowerCase();
            newWordsList.add(newWord);
        }
        return newWordsList;
    }

    private void removeExclusions(List<String> source) {
        source.removeIf(exclusions::contains);
    }
}
