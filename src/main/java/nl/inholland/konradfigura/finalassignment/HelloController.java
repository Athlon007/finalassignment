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
            setWarningText(builder.toString());
        }

        // TODO: Show dashboard ONLY if user successfully logs in.
        try {
            HelloApplication.getInstance().loadView(Views.Dashboard);
        }
        catch (Exception exception) {
            setWarningText("Something went wrong while loading the dashboard.");
            exception.printStackTrace();
            // TODO: Add error logger.
        }
    }

    private boolean isPasswordValid(StringBuilder failReason) {
        // TODO: Implement password validation.
        failReason.append("User does not exist.");
        return false;
    }

    private void setWarningText(String text) {
        lblWarning.setText(text);
    }
}