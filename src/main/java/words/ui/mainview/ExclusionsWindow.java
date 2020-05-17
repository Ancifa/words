package words.ui.mainview;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import words.processor.Processor;

public class ExclusionsWindow {
    private final Dialog dialog;

    public ExclusionsWindow() {
        dialog = new Dialog(new Label("List of built-in exclusions"));
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.add(new Label(Processor.exclusions.toString()));
        dialog.add(contentLayout);
        dialog.setWidth("550px");
    }

    public void open() {
        dialog.open();
    }
}
