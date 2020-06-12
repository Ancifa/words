package words.ui.mainview;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import words.processor.Processor;

public class ExclusionsWindow extends Dialog {

    public ExclusionsWindow() {
        add(new Label("List of built-in exclusions"));
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.add(new Label(Processor.exclusions.toString()));
        add(contentLayout);
        setWidth("550px");
    }
}
