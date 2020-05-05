package words.ui.mainview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import lombok.Getter;
import words.ui.ResultBlock;

@Route
@Getter
public class MainView extends VerticalLayout {
    private TextArea textArea;
    private Button startButtonWords;
    private Button startButtonSentences;
    private Label countLabel;
    private final ResultBlock resultBlock;

    public MainView(MainViewPresenter mainViewPresenter, ResultBlock resultBlock) {
        this.resultBlock = resultBlock;
        initElements();
        buildLayout();
        mainViewPresenter.setView(this);
        mainViewPresenter.initListeners();
    }

    private void initElements() {
        buildTextArea();
        buildButtons();
        buildCountLabel();
    }

    private void buildTextArea() {
        textArea = new TextArea();
        textArea.setWidth("700px");
        textArea.setHeight("300px");
        textArea.setClearButtonVisible(true);
        textArea.setPlaceholder("Copy your text here");
    }

    private void buildButtons() {
        startButtonWords = new Button("START W");
        startButtonSentences = new Button("START S");
    }

    private void buildCountLabel() {
        countLabel = new Label();
        countLabel.setVisible(false);
    }

    private Label buildLogoLabel() {
        Label logo = new Label("WORDS");
        logo.getStyle().set("font-size", "40px");
        logo.getStyle().set("font-style", "italic");
        logo.getStyle().set("font-weight", "bold");
        logo.getStyle().set("color", "blue");

        return logo;
    }

    private void buildLayout() {
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.add(startButtonWords, startButtonSentences);
        add(buildLogoLabel(), textArea, buttonsLayout, countLabel, resultBlock);
    }

}
