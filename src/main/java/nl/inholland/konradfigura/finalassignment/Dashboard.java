package nl.inholland.konradfigura.finalassignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import nl.inholland.konradfigura.finalassignment.Model.User;
import nl.inholland.konradfigura.finalassignment.Model.UserLoadable;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Dashboard implements Initializable, UserLoadable {
    @FXML
    private AnchorPane root;

    @FXML
    private TabPane tabContainer;

    @FXML
    private Tab tabLending;

    @FXML
    private Tab tabCollection;

    @FXML
    private Tab tabMembers;

    @FXML
    private Label lblWelcome;

    @FXML
    private TableView tblItems;

    @FXML
    private TableView tblMembers;

    @FXML
    private AnchorPane paneAddMember;

    @FXML
    private AnchorPane paneAddItem;

    @FXML
    private TextField txtNewMemberFirstName;

    @FXML
    private TextField txtNewMemberLastName;

    @FXML
    private DatePicker dpNewMemberBirthdate;

    @FXML
    private TableColumn tblMembersId;

    @FXML
    private TableColumn tblMembersFirstName;

    @FXML
    private TableColumn tblMembersLastName;

    @FXML
    private TableColumn tblMembersBirthdate;

    private User currentUser;
    private User editingUser;
    private boolean editUserMode;

    @FXML
    private Label lblErrorAddMember;

    @FXML
    private PasswordField pwdNewMemberPassword;

    @FXML
    private Button btnAddMember;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabContainer.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, tab, t1) -> {
                    // TODO: Implement tab changing actions.
                    if (tab == tabMembers) {
                        paneAddMember.setVisible(false);
                    }
                }
        );
        tabContainer.getSelectionModel().select(tabLending);

        tblMembersId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblMembersFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tblMembersLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tblMembersBirthdate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        loadTableMembers();

        lblErrorAddMember.setText("");
    }

    public void loadUser(User user) {
        this.currentUser = user;
        lblWelcome.setText(String.format("Welcome %s", currentUser.getFirstName()));
    }

    private void loadTableMembers() {
        tblMembers.getItems().clear();
        tblMembers.getItems().addAll(HelloApplication.getDatabase().getAll());
    }

    @FXML
    protected void onAddMemberOpenMenuClick(ActionEvent event) {
        txtNewMemberFirstName.setText("");
        txtNewMemberLastName.setText("");
        pwdNewMemberPassword.setText("");
        dpNewMemberBirthdate.setValue(LocalDate.now());
        btnAddMember.setText("Add member");
        editUserMode = false;

        paneAddMember.setVisible(true);
    }

    @FXML
    protected void onEditMemberOpenMenuClick(ActionEvent event) {
        if (tblMembers.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        editingUser = (User)tblMembers.getSelectionModel().getSelectedItem();

        btnAddMember.setText("Edit member");
        editUserMode = true;

        txtNewMemberFirstName.setText(editingUser.getFirstName());
        txtNewMemberLastName.setText(editingUser.getLastName());
        pwdNewMemberPassword.setText(editingUser.getPassword());
        dpNewMemberBirthdate.setValue(editingUser.getBirthdate());

        paneAddMember.setVisible(true);
    }

    @FXML
    protected void onCancelAddMemberClick(ActionEvent event) {
        paneAddMember.setVisible(false);
    }

    @FXML
    protected void onAddItemClick(ActionEvent event) {
        paneAddItem.setVisible(true);
        // TODO: Clean text fields.
    }

    @FXML
    protected void onCancelAddItemClick(ActionEvent event) {
        paneAddItem.setVisible(false);
    }

    @FXML
    protected void onAddMemberClick(ActionEvent event) {
        root.requestFocus();

        lblErrorAddMember.setText("");
        String errors = "";

        String firstName = txtNewMemberFirstName.getText();
        String lastName = txtNewMemberLastName.getText();
        String password = pwdNewMemberPassword.getText();

        LocalDate birthdate = dpNewMemberBirthdate.getValue();

        if (firstName.isEmpty()) {
            errors += "First name missing\n";
        }
        if (lastName.isEmpty()) {
            errors += "Last name missing\n";
        }
        if (password.isEmpty()) {
            errors += "Password missing\n";
        }
        if (birthdate == null) {
            errors += "Birth date missing\n";
        }

        if (errors.length() > 0) {
            lblErrorAddMember.setText(errors);
            return;
        }

        if (editUserMode) {
            HelloApplication.getDatabase().editUser(editingUser, firstName, lastName, password, birthdate);
        }
        else {
            HelloApplication.getDatabase().add(firstName, lastName, birthdate, "password1");
        }

        loadTableMembers();
        paneAddMember.setVisible(false);
    }

    @FXML
    protected void onDeleteMember(ActionEvent event) {
        User selectedPerson = (User)tblMembers.getSelectionModel().getSelectedItem();

        if (selectedPerson == null) {
            return;
        }

        if (selectedPerson == currentUser) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Oops!");
            a.setHeaderText("Cannot remove self, dummy.");
            a.show();
            return;
        }

        HelloApplication.getDatabase().delete(selectedPerson);
        loadTableMembers();
    }
}

