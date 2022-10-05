package nl.inholland.konradfigura.finalassignment;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import nl.inholland.konradfigura.finalassignment.Database.Database;
import nl.inholland.konradfigura.finalassignment.Model.User;

public class HelloController {

    @FXML
    private Label lblWarning;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    protected void onLoginButtonClick(ActionEvent event) {
        login();
    }

    private void login() {
        StringBuilder builder = new StringBuilder();
        User user = HelloApplication.getDatabase().getUser(txtUsername.getText(), txtPassword.getText());

        if (user == null) {
            setWarningText("User does not exist.");
            return;
        }

        try {
            HelloApplication.loadView(Views.DASHBOARD, user);
        }
        catch (Exception exception) {
            setWarningText("Something went wrong while loading the dashboard.");
            exception.printStackTrace();
            // TODO: Add error logger.
        }
    }

    private void setWarningText(String text) {
        lblWarning.setText(text);
    }

    @FXML
    private void onKeyPressed(KeyEvent e) {
        if (e.getCode().toString().equals("ENTER")) {
            login();
        }
    }
}