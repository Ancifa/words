package words.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import words.processor.Sentence;
import words.processor.Word;
import words.service.DictionaryService;

import java.util.List;

public class ResultBlock extends VerticalLayout {

    public ResultBlock() {
        getStyle().set("padding-left", "0px");
        setVisible(false);
    }

    private void buildHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.getStyle().set("border-bottom", "solid");
        header.getStyle().set("border-bottom-width", "1px");
        header.getStyle().set("margin-top", "0px");
        header.setWidth("500px");

        Label wordLabel = new Label("Word");
        wordLabel.getStyle().set("width", "170px");

        Label quantityLabel = new Label("Quantity");
        quantityLabel.getStyle().set("width", "140px");

        Label sentenceLabel = new Label("Sentence");
        sentenceLabel.getStyle().set("width", "100px");

        header.add(wordLabel, quantityLabel, sentenceLabel);
        add(header);
    }

    public void buildResultRows(List<Word> result, Sentence sentence) {
        buildHeader();

        int count = 0;
        for (Word word : result) {
            HorizontalLayout hl = new HorizontalLayout();
//            hl.getStyle().set("border-bottom", "solid");
            hl.getStyle().set("border-bottom-width", "1px");
            hl.getStyle().set("margin-top", "0px");
            hl.setWidth("500px");
            if (count % 2 == 0) {
                hl.getStyle().set("background-color", "lavender");
            }

            Button wordButton = new Button(word.getItem());
            UiUtils.setButtonAsLabelStyle(wordButton, 170);
            wordButton.addClickListener(c -> createWindow(word));

            Label quantityLabel = new Label(word.getQuantity() + "");
            quantityLabel.getStyle().set("width", "100px");

            Button sentenceButton = new Button(Integer.toString(word.getSentenceNumber()));
            UiUtils.setButtonAsLabelStyle(sentenceButton, 100);

            sentenceButton.addClickListener(c -> {
                Dialog dialog = new Dialog();
                dialog.add(sentence.getSentencesMap().get(word.getSentenceNumber()));
                dialog.open();
            });

            hl.add(wordButton, quantityLabel, sentenceButton);
            add(hl);
            count++;
        }
    }

    private void createWindow(Word word) {
        String item = word.getItem();
        Dialog dialog = new Dialog(new Label(item.toUpperCase()));

        VerticalLayout contentLayout = new VerticalLayout();
        List<String> definitionList = DictionaryService.getDefinition(item, true);
        definitionList.forEach(definition -> {
            Label contentLabel = new Label(definition);
            contentLayout.add(contentLabel);
        });

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button closeButton = new Button("Close");
        closeButton.addClickListener(c -> dialog.close());
        buttonsLayout.add(closeButton);

        dialog.add(contentLayout, buttonsLayout);

        dialog.setWidth("550px");
        dialog.setHeight("250px");

        dialog.open();
    }
}
