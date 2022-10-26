package nl.inholland.konradfigura.finalassignment.ui.customcontrols;

import javafx.scene.control.TextField;

public class DigitOnlyTextField extends TextField {
    public DigitOnlyTextField() {
        super();
        this.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                this.setText(newValue.replaceAll("\\D", ""));
            }
        }));
    }
}
