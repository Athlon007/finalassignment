package nl.inholland.konradfigura.finalassignment;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import nl.inholland.konradfigura.finalassignment.model.User;

public class HelloController {

    @FXML
    private Label lblWarning;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    protected void onLoginButtonClick() {
        login();
    }

    private void login() {
        User user = HelloApplication.getUsers().getUser(txtUsername.getText(), txtPassword.getText());

        if (user == null) {
            setWarningText("User does not exist, or password is incorrect.");
            return;
        }

        try {
            Dashboard dashboard = new Dashboard();
            HelloApplication.loadView("dashboard.fxml", dashboard);
            dashboard.loadUser(user);
        }
        catch (Exception exception) {
            setWarningText("Something went wrong while loading the dashboard.");
            exception.printStackTrace();
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