package nl.inholland.konradfigura.finalassignment;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import nl.inholland.konradfigura.finalassignment.Model.Exceptions.BookNotAvailableException;
import nl.inholland.konradfigura.finalassignment.Model.Exceptions.BookNotFoundException;
import nl.inholland.konradfigura.finalassignment.Model.Exceptions.OvertimeException;
import nl.inholland.konradfigura.finalassignment.Model.Exceptions.UserNotFoundException;
import nl.inholland.konradfigura.finalassignment.Model.LibraryItem;
import nl.inholland.konradfigura.finalassignment.Model.User;
import nl.inholland.konradfigura.finalassignment.Model.UserLoadable;

import javax.swing.*;
import java.io.FileNotFoundException;
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

    @FXML
    private Label lblErrorAddMember;

    @FXML
    private PasswordField pwdNewMemberPassword;

    @FXML
    private Button btnAddMember;

    // COLLECTION
    @FXML
    private TableColumn tblItemsItemCode;
    @FXML
    private TableColumn tblItemsAvailable;
    @FXML
    private TableColumn tblItemsTitle;
    @FXML
    private TableColumn tblItemsAuthor;
    @FXML
    private TextField txtAddItemTitle;
    @FXML
    private TextField txtAddItemAuthor;
    @FXML
    private Button btnAddItem;
    @FXML
    private Label lblErrorAddItem;

    // LENDING/RECEIVING
    @FXML
    private TextField txtLendItemCode;
    @FXML
    private TextField txtLendMemberId;
    @FXML
    private Label lblLendError;

    @FXML
    private TextField txtReceiveCode;
    @FXML
    private Label lblReceiveError;

    @FXML
    private TextField txtItemSearch;


    // VARIABLES.
    private final String GOOD_LABEL_CLASS = "good-label";
    private final String ERROR_LABEL_CLASS = "error-label";

    private User currentUser;
    private User editingUser;
    private boolean editUserMode;

    private LibraryItem editingItem;
    private boolean editItemMode;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabContainer.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldTab, newTab) -> {
                    if (newTab == tabMembers) {
                        paneAddMember.setVisible(false);
                        loadTableMembers();
                    }
                    else if (newTab == tabCollection) {
                        paneAddItem.setVisible(false);
                        loadTableItems();
                    }
                    else if (newTab == tabLending) {
                        lblLendError.setText("");
                        lblReceiveError.setText("");
                    }
                }
        );
        tabContainer.getSelectionModel().select(tabLending);

        tblMembersId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblMembersFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tblMembersLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tblMembersBirthdate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));

        tblItemsItemCode.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblItemsAvailable.setCellValueFactory(new PropertyValueFactory<>("availableHumanReadable"));
        tblItemsTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tblItemsAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));

        lblErrorAddMember.setText("");

        // Prevent text boxes that cannot accept digits... to not accept digits.
        txtLendItemCode.textProperty().addListener(new NoDigitTextFieldListener(txtLendItemCode));
        txtLendMemberId.textProperty().addListener(new NoDigitTextFieldListener(txtLendMemberId));
        txtReceiveCode.textProperty().addListener(new NoDigitTextFieldListener(txtReceiveCode));
        lblLendError.setText("");
        lblReceiveError.setText("");
    }

    public void loadUser(User user) {
        this.currentUser = user;
        lblWelcome.setText(String.format("Welcome %s", currentUser.getFirstName()));
    }

    private void loadTableMembers() {
        tblMembers.getItems().clear();
        tblMembers.getItems().addAll(HelloApplication.getDatabase().getAll());
    }

    private void loadTableItems() {
        tblItems.getItems().clear();
        tblItems.getItems().addAll(HelloApplication.getLibrary().getAll());
    }

    @FXML
    protected void onAddMemberOpenMenuClick(ActionEvent event) {
        txtNewMemberFirstName.setText("");
        txtNewMemberLastName.setText("");
        pwdNewMemberPassword.setText("");
        lblErrorAddItem.setText("");
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
        lblErrorAddItem.setText("");

        paneAddMember.setVisible(true);
    }

    @FXML
    protected void onCancelAddMemberClick(ActionEvent event) {
        paneAddMember.setVisible(false);
    }

    @FXML
    protected void onAddItemOpenMenuClick(ActionEvent event) {
        paneAddItem.setVisible(true);
        btnAddItem.setText("Add item");
        editItemMode = false;
        txtAddItemTitle.setText("");
        txtAddItemAuthor.setText("");
        lblErrorAddItem.setText("");
    }

    @FXML
    protected void onCancelAddItemClick(ActionEvent event) {
        paneAddItem.setVisible(false);
    }

    @FXML
    protected void onAddMemberClick(ActionEvent event) {
        root.requestFocus();

        lblErrorAddMember.setText("");

        String firstName = txtNewMemberFirstName.getText();
        String lastName = txtNewMemberLastName.getText();
        String password = pwdNewMemberPassword.getText();
        LocalDate birthdate = dpNewMemberBirthdate.getValue();

        try {
            if (editUserMode) {
                HelloApplication.getDatabase().editUser(editingUser, firstName, lastName, password, birthdate);
            } else {
                HelloApplication.getDatabase().add(firstName, lastName, birthdate, password);
            }
        }
        catch (UserNotFoundException ex) {
            lblErrorAddMember.setText(ex.getMessage());
        }
        catch (IllegalArgumentException ex) {
            lblErrorAddMember.setText(ex.getMessage());
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
            // TODO: Move that to UserDatabase.
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Oops!");
            a.setHeaderText("Cannot remove self, dummy.");
            a.show();
            return;
        }

        try {
            HelloApplication.getDatabase().delete(selectedPerson);
            loadTableMembers();
        }
        catch (UserNotFoundException ex) {
            // TODO: Add this.
        }
    }

    @FXML
    protected void onAddItemClick(ActionEvent event) {
        String title = txtAddItemTitle.getText();
        String author = txtAddItemAuthor.getText();

        try {
            if (editItemMode) {
                HelloApplication.getLibrary().edit(editingItem, title, author);
            }
            else {
                HelloApplication.getLibrary().add(title, author);
            }
            paneAddItem.setVisible(false);
            loadTableItems();
        } catch (Exception ex) {
            lblErrorAddItem.setText(ex.getMessage());
        }
    }

    @FXML
    protected void onLendClick(ActionEvent event) {
        setLabelRed(lblLendError);
        lblLendError.setText("");
        String bookCodeString = txtLendItemCode.getText();
        String lenderCodeString = txtLendMemberId.getText();
        if (!isLendInputValid(bookCodeString, lenderCodeString)) {
            return;
        }

        int bookCode = Integer.parseInt(txtLendItemCode.getText());
        int lenderCode = Integer.parseInt(txtLendMemberId.getText());

        LibraryItem book = HelloApplication.getLibrary().getById(bookCode);

        try {
            User user = HelloApplication.getDatabase().getById(lenderCode);
            HelloApplication.getLibrary().lendBook(book, user, LocalDate.now());

            setLabelGreen(lblLendError);
            lblLendError.setText(String.format("Book %s [%d] has been borrowed to member %s",
                    book.getTitle(), book.getId(), user.getFirstName()));
            txtLendItemCode.setText("");
            txtLendMemberId.setText("");
        }
        catch (Exception ex) {
            lblLendError.setText(ex.getMessage());
        }
    }

    private boolean isLendInputValid(String bookCode, String lenderCode) {
        String issues = "";
        if (bookCode.isEmpty()) {
            issues += "Item code is missing.\n";
        }

        if (lenderCode.isEmpty()) {
            issues += "Lender code is missing.\n";
        }

        lblLendError.setText(issues);
        return issues.length() == 0;
    }

    @FXML
    protected void onReceiveClick(ActionEvent event) {
        lblReceiveError.setText("");
        setLabelRed(lblReceiveError);;

        if (txtReceiveCode.getText().isEmpty()) {
            lblReceiveError.setText("Item code is empty.");
            return;
        }

        int itemCode = Integer.parseInt(txtReceiveCode.getText());

        try {
            LibraryItem item = HelloApplication.getLibrary().getById(itemCode);
            HelloApplication.getLibrary().returnBook(item);

            setLabelGreen(lblReceiveError);
            lblReceiveError.setText("Book has been returned!");
            txtReceiveCode.setText("");
        }
        catch (BookNotFoundException ex) {
            lblReceiveError.setText("Book not found.");
        }
        catch (OvertimeException ex) {
            lblReceiveError.setText("Book was returned after more than 3 weeks!");
        }
        catch (NullPointerException ex) {
            lblReceiveError.setText("Book is not borrowed.");
        }
    }

    private void setLabelGreen(Label label) {
        if (!label.getStyleClass().contains(GOOD_LABEL_CLASS)) {
            label.getStyleClass().add(GOOD_LABEL_CLASS);
        }

        if (label.getStyleClass().contains(ERROR_LABEL_CLASS)) {
            label.getStyleClass().remove(ERROR_LABEL_CLASS);
        }
    }

    private void setLabelRed(Label label) {
        if (!label.getStyleClass().contains(ERROR_LABEL_CLASS)) {
            label.getStyleClass().add(ERROR_LABEL_CLASS);
        }

        if (label.getStyleClass().contains(GOOD_LABEL_CLASS)) {
            label.getStyleClass().remove(GOOD_LABEL_CLASS);
        }
    }

    @FXML
    private void onEditItemClick(ActionEvent event) {
        LibraryItem item = (LibraryItem)tblItems.getSelectionModel().getSelectedItem();
        lblErrorAddItem.setText("");

        if (item == null) {
            return;
        }

        editingItem = item;
        editItemMode = true;

        btnAddItem.setText("Edit item");
        paneAddItem.setVisible(true);

        txtAddItemTitle.setText(editingItem.getTitle());
        txtAddItemAuthor.setText(editingItem.getAuthor());
    }

    @FXML
    private void onDeleteItemClick(ActionEvent event) {
        LibraryItem item = (LibraryItem)tblItems.getSelectionModel().getSelectedItem();
        if (item == null) {
            return;
        }

        try {
            HelloApplication.getLibrary().delete(item);
            loadTableItems();
        } catch (BookNotFoundException ex) {
            // TODO: Add lblWarning
        }
    }

    @FXML
    private void onSearchTyped(ActionEvent event) {
        String query = ((TextField)event.getSource()).getText();
        if (query.isEmpty()) {
            loadTableItems();
        }
        else {
            loadTableItems(query);
        }
    }

    private void loadTableItems(String query) {
        tblItems.getItems().clear();
        tblItems.getItems().addAll(HelloApplication.getLibrary().getAll(query));
    }
}

