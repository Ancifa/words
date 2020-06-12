package words.ui.mainview;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import words.config.AppContext;
import words.processor.Processor;
import words.processor.Sentence;
import words.processor.Word;

import java.util.List;
import java.util.Map;

public class MainViewPresenter {
    private final Processor processor;
    private MainView view;

    public MainViewPresenter() {
        processor = AppContext.getContext().getBean(Processor.class);
    }

    void setView(MainView view) {
        this.view = view;
    }

    void initListeners() {
        view.getStartButtonWords().addClickListener(click -> {
            String text = view.getTextArea().getValue();
            processor.setRemoveExclusionsNeeded(view.getUseExclusionsChkBox().getValue());
            List<Word> words = processor.processWords(text);
            viewResult(words, processor.getSentence());
        });

        view.getStartButtonSentences().addClickListener(click -> {
            String text = view.getTextArea().getValue();
            Map<Integer, String> sentences = processor.processSentences(text);
            viewResult(sentences);
        });
    }

    private void viewResult(List<Word> result, Sentence sentence) {
        view.getResultBlock().removeAll();
        view.getResultBlock().buildResultRows(result, sentence, view.getDictionaryCheckBox());
        view.getResultBlock().setVisible(true);

        view.getCountLabel().setText(processor.getCounter() + " words");
        view.getCountLabel().setVisible(true);
    }

    private void viewResult(Map<Integer, String> result) {
        view.getResultBlock().removeAll();

        for (Map.Entry<Integer, String> sentence : result.entrySet()) {
            HorizontalLayout layout = new HorizontalLayout();
            layout.add(new Label(sentence.getKey().toString()), new Label(sentence.getValue()));
            view.getResultBlock().add(layout);
        }

        view.getResultBlock().setVisible(true);

        view.getCountLabel().setText(processor.getCounter() + " sentences");
        view.getCountLabel().setVisible(true);
    }
}
