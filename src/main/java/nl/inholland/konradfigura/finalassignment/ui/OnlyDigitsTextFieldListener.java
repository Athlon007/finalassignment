package nl.inholland.konradfigura.finalassignment.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class OnlyDigitsTextFieldListener implements ChangeListener<String> {
    private final TextField field;

    public OnlyDigitsTextFieldListener(TextField field) {
        this.field = field;
    }

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        if (!newValue.matches("\\d*")) {
            field.setText(newValue.replaceAll("\\D", ""));
        }
    }
}
