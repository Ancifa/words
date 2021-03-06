package words.ui.mainview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import lombok.Getter;
import words.ui.ResultBlock;
import words.ui.UiUtils;

@Route
@Getter
public class MainView extends VerticalLayout {
    public static final String COLLEGIATE = "Collegiate";
    public static final String THESAURUS = "Thesaurus Dictionary";

    private TextArea textArea;
    private VerticalLayout filtersLayout;
    private Checkbox useExclusionsChkBox;
    private Checkbox dictionaryCheckBox;
    private Button startButtonWords;
    private Button startButtonSentences;
    private Label countLabel;
    private final ResultBlock resultBlock;
    private final ExclusionsWindow exclusionsWindow;

    public MainView(MainViewPresenter mainViewPresenter, ResultBlock resultBlock, ExclusionsWindow exclusionsWindow) {
        this.resultBlock = resultBlock;
        this.exclusionsWindow = exclusionsWindow;
        initElements();
        buildLayout();
        mainViewPresenter.setView(this);
        mainViewPresenter.initListeners();
    }

    private void initElements() {
        buildTextArea();
        buildFilterElements();
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

    private void buildFilterElements() {
        filtersLayout = new VerticalLayout();

        HorizontalLayout chkBoxLyt = new HorizontalLayout();
        useExclusionsChkBox = new Checkbox();
        Button exclusionsButton = new Button("Use built-in exclusions");
        UiUtils.setButtonAsLabelStyle(exclusionsButton);
        exclusionsButton.addClickListener(click -> exclusionsWindow.open());
        chkBoxLyt.add(useExclusionsChkBox, exclusionsButton);

        dictionaryCheckBox = new Checkbox(THESAURUS);

        filtersLayout.add(chkBoxLyt, dictionaryCheckBox);
    }

    private void buildButtons() {
        startButtonWords = new Button("Extract Words");
        startButtonSentences = new Button("Extract Sentences");
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
        add(buildLogoLabel(), textArea, filtersLayout, buttonsLayout, countLabel, resultBlock);
    }

}
