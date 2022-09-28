package nl.inholland.konradfigura.finalassignment;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class HelloController {

    @FXML
    private Label lblWarning;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    protected void onLoginButtonClick(ActionEvent event) {
        StringBuilder builder = new StringBuilder();
        boolean valid = isPasswordValid(builder);

        if (!valid) {
            lblWarning.setText(builder.toString());
        }
    }

    private boolean isPasswordValid(StringBuilder failReason) {
        // TODO: Implement it :D
        failReason.append("User does not exist.");
        return false;
    }
}