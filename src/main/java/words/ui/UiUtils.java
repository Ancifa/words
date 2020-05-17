package words.ui;

import com.vaadin.flow.component.button.Button;

public class UiUtils {

    public static void setButtonAsLabelStyle(Button button) {
        setButtonAsLabelStyle(button, -1);
    }

    public static void setButtonAsLabelStyle(Button button, int width) {
        button.getStyle().set("padding", "0px");
        button.getStyle().set("margin-top", "1px");
        button.getStyle().set("height", "0px");
        button.getStyle().set("width", width < 0 ? "auto" : width + "px");
        button.getStyle().set("border", "none");
        button.getStyle().set("background", "none");
        button.getStyle().set("cursor", "pointer");
    }

}
