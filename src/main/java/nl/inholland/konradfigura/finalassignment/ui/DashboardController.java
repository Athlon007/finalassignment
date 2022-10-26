package nl.inholland.konradfigura.finalassignment.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import nl.inholland.konradfigura.finalassignment.ApplicationMain;
import nl.inholland.konradfigura.finalassignment.model.exceptions.BookNotBorrowedException;
import nl.inholland.konradfigura.finalassignment.model.exceptions.BookNotFoundException;
import nl.inholland.konradfigura.finalassignment.model.exceptions.OvertimeException;
import nl.inholland.konradfigura.finalassignment.model.exceptions.MemberNotFoundException;
import nl.inholland.konradfigura.finalassignment.model.LibraryItem;
import nl.inholland.konradfigura.finalassignment.model.Member;
import nl.inholland.konradfigura.finalassignment.model.User;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private TabPane tabContainer;
    @FXML
    private Label lblWelcome;
    @FXML
    private TableView<Member> tblMembers;
    @FXML
    private TableView<LibraryItem> tblItems;
    @FXML
    private TableColumn<LibraryItem, String> tblItemsAvailable;
    @FXML
    private TextField txtMemberSearch;

    // ADD/EDIT MEMBER PANE
    @FXML
    private AnchorPane paneAddMember;
    @FXML
    private TextField txtNewMemberFirstName;
    @FXML
    private TextField txtNewMemberLastName;
    @FXML
    private DatePicker dpNewMemberBirthdate;
    @FXML
    private Label lblErrorAddMember;
    @FXML
    private Button btnAddMember;

    // COLLECTION
    @FXML
    private AnchorPane paneAddItem;
    @FXML
    private TextField txtAddItemTitle;
    @FXML
    private TextField txtAddItemAuthor;
    @FXML
    private Button btnAddItem;
    @FXML
    private Label lblErrorAddItem;
    @FXML
    private TextField txtItemSearch;

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
    private Label lblMembersError;
    @FXML
    private Label lblCollectionError;

    // VARIABLES.
    private static final String GOOD_LABEL_CLASS = "good-label";
    private static final String ERROR_LABEL_CLASS = "error-label";
    private Member editingMember;
    private LibraryItem editingItem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Tab switching.
        initTabSwitcher();

        // Select last tab on load.
        tabContainer.getSelectionModel().select(tabContainer.getTabs().size() - 1);

        // Set table columns Items
        tblItemsAvailable.setCellValueFactory(s -> new SimpleStringProperty(s.getValue().isAvailable() ? "Yes" : "No"));

        // Prevent text boxes that cannot accept digits... to not accept digits.
        txtLendItemCode.textProperty().addListener(new OnlyDigitsTextFieldListener(txtLendItemCode));
        txtLendMemberId.textProperty().addListener(new OnlyDigitsTextFieldListener(txtLendMemberId));
        txtReceiveCode.textProperty().addListener(new OnlyDigitsTextFieldListener(txtReceiveCode));

        // Search fields.
        initSearchBars();
    }

    public void loadUser(User user) {
        lblWelcome.setText(String.format("Welcome %s", user.getUsername()));
    }

    private void loadTableMembers() {
        tblMembers.getItems().clear();
        tblMembers.setItems(FXCollections.observableArrayList(ApplicationMain.getMembers().getAll()));
    }

    private void loadTableMembers(String query) {
        tblMembers.getItems().clear();
        tblMembers.setItems(FXCollections.observableArrayList(ApplicationMain.getMembers().getAll(query)));
    }

    private void loadTableItems() {
        tblItems.getItems().clear();
        tblItems.setItems(FXCollections.observableArrayList(ApplicationMain.getLibrary().getAll()));
    }

    private void loadTableItems(String query) {
        tblItems.getItems().clear();
        tblItems.getItems().addAll(FXCollections.observableArrayList(ApplicationMain.getLibrary().getAll(query)));
    }

    private void initTabSwitcher() {
        tabContainer.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldTab, newTab) -> {
                    switch (newTab.getId()) {
                        case "tabMembers":
                            paneAddMember.setVisible(false);
                            lblMembersError.setText("");
                            txtMemberSearch.setText("");
                            loadTableMembers();
                            break;
                        case "tabCollection":
                            paneAddItem.setVisible(false);
                            lblCollectionError.setText("");
                            txtItemSearch.setText("");
                            loadTableItems();
                            break;
                        case "tabLending":
                            lblLendError.setText("");
                            lblReceiveError.setText("");
                            break;
                        default: break;
                    }
                }
        );
    }

    private void initSearchBars() {
        txtItemSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                loadTableItems();
            } else {
                loadTableItems(newValue);
            }
        });

        txtMemberSearch.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                loadTableMembers();
            } else {
                loadTableMembers(newValue);
            }
        }));
    }

    @FXML
    protected void onAddMemberOpenMenuClick() {
        txtNewMemberFirstName.setText("");
        txtNewMemberLastName.setText("");
        lblErrorAddItem.setText("");
        dpNewMemberBirthdate.setValue(LocalDate.now());
        btnAddMember.setText("Add member");
        editingMember = null;

        paneAddMember.setVisible(true);
    }

    @FXML
    protected void onEditMemberOpenMenuClick() {
        if (tblMembers.getSelectionModel().getSelectedItem() == null) {
            lblMembersError.setText("Select member to edit first.");
            return;
        }
        editingMember = tblMembers.getSelectionModel().getSelectedItem();
        lblMembersError.setText("");

        btnAddMember.setText("Edit member");

        txtNewMemberFirstName.setText(editingMember.getFirstName());
        txtNewMemberLastName.setText(editingMember.getLastName());
        dpNewMemberBirthdate.setValue(editingMember.getBirthdate());
        lblErrorAddItem.setText("");

        paneAddMember.setVisible(true);
    }

    @FXML
    protected void onCancelAddMemberClick() {
        paneAddMember.setVisible(false);
    }

    @FXML
    protected void onAddItemOpenMenuClick() {
        paneAddItem.setVisible(true);
        btnAddItem.setText("Add item");
        editingItem = null;
        txtAddItemTitle.setText("");
        txtAddItemAuthor.setText("");
        lblErrorAddItem.setText("");
    }

    @FXML
    protected void onCancelAddItemClick() {
        paneAddItem.setVisible(false);
    }

    @FXML
    protected void onAddMemberClick() {
        lblErrorAddMember.setText("");

        String firstName = txtNewMemberFirstName.getText();
        String lastName = txtNewMemberLastName.getText();

        // A workaround for a bug in JavaFX listed here:
        // https://bugs.openjdk.org/browse/JDK-8092295?page=com.atlassian.jira.plugin.system.issuetabpanels:changehistory-tabpanel
        String dpText = dpNewMemberBirthdate.getEditor().getText();
        dpNewMemberBirthdate.setValue(dpNewMemberBirthdate.getConverter().fromString(dpText));
        LocalDate birthdate = dpNewMemberBirthdate.getValue();

        try {
            if (editingMember != null) {
                ApplicationMain.getMembers().editUser(editingMember, firstName, lastName, birthdate);
            } else {
                ApplicationMain.getMembers().add(firstName, lastName, birthdate);
            }

            loadTableMembers();
            paneAddMember.setVisible(false);
        }
        catch (MemberNotFoundException | IllegalArgumentException ex) {
            lblErrorAddMember.setText(ex.getMessage());
        }
    }

    @FXML
    protected void onDeleteMember() {
        Member selectedPerson = tblMembers.getSelectionModel().getSelectedItem();

        if (selectedPerson == null) {
            lblMembersError.setText("Select member to delete first.");
            return;
        }

        if (ApplicationMain.getLibrary().isMemberBorrowingBook(selectedPerson)) {
            lblMembersError.setText("Can't delete member: Member has borrowed a book.");
            return;
        }

        lblMembersError.setText("");

        try {
            ApplicationMain.getMembers().delete(selectedPerson);
            loadTableMembers();
        }
        catch (MemberNotFoundException ex) {
            lblMembersError.setText(ex.getMessage());
        }
    }

    @FXML
    protected void onAddItemClick() {
        String title = txtAddItemTitle.getText();
        String author = txtAddItemAuthor.getText();

        try {
            if (editingItem != null) {
                ApplicationMain.getLibrary().edit(editingItem, title, author);
            }
            else {
                ApplicationMain.getLibrary().add(title, author);
            }
            paneAddItem.setVisible(false);
            loadTableItems();
        } catch (Exception ex) {
            lblErrorAddItem.setText(ex.getMessage());
        }
    }

    @FXML
    protected void onLendClick() {
        setLabelRed(lblLendError);
        lblLendError.setText("");
        String bookCodeString = txtLendItemCode.getText();
        String lenderCodeString = txtLendMemberId.getText();
        if (!isLendInputValid(bookCodeString, lenderCodeString)) {
            return;
        }

        int bookCode = Integer.parseInt(bookCodeString);
        int lenderCode = Integer.parseInt(lenderCodeString);

        LibraryItem book = ApplicationMain.getLibrary().getById(bookCode);

        try {
            Member member = ApplicationMain.getMembers().getById(lenderCode);
            ApplicationMain.getLibrary().lendBook(book, member, LocalDate.now());

            setLabelGreen(lblLendError);
            lblLendError.setText(String.format("Book %s [%d] has been borrowed to member %s",
                    book.getTitle(), book.getId(), member.getFirstName()));
        } catch (Exception ex) {
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
    protected void onReceiveClick() {
        lblReceiveError.setText("");
        setLabelRed(lblReceiveError);

        if (txtReceiveCode.getText().isEmpty()) {
            lblReceiveError.setText("Item code is empty.");
            return;
        }

        int itemCode = Integer.parseInt(txtReceiveCode.getText());

        try {
            LibraryItem item = ApplicationMain.getLibrary().getById(itemCode);
            ApplicationMain.getLibrary().returnBook(item);

            setLabelGreen(lblReceiveError);
            lblReceiveError.setText("Book has been returned!");
            txtReceiveCode.setText("");
        } catch (BookNotFoundException | BookNotBorrowedException ex) {
            lblReceiveError.setText(ex.getMessage());
        } catch (OvertimeException ex) {
            lblReceiveError.setText(ex.getMessage());
            txtReceiveCode.setText("");
        } catch (NullPointerException ex) {
            lblReceiveError.setText("Book is not borrowed.");
        }
    }

    private void setLabelGreen(Label label) {
        if (!label.getStyleClass().contains(GOOD_LABEL_CLASS)) {
            label.getStyleClass().add(GOOD_LABEL_CLASS);
        }

        label.getStyleClass().remove(ERROR_LABEL_CLASS);
    }

    private void setLabelRed(Label label) {
        if (!label.getStyleClass().contains(ERROR_LABEL_CLASS)) {
            label.getStyleClass().add(ERROR_LABEL_CLASS);
        }

        label.getStyleClass().remove(GOOD_LABEL_CLASS);
    }

    @FXML
    private void onEditItemClick() {
        LibraryItem item = tblItems.getSelectionModel().getSelectedItem();
        lblErrorAddItem.setText("");

        if (item == null) {
            lblCollectionError.setText("Select item to edit first.");
            return;
        }
        lblCollectionError.setText("");

        editingItem = item;

        btnAddItem.setText("Edit item");
        paneAddItem.setVisible(true);

        txtAddItemTitle.setText(editingItem.getTitle());
        txtAddItemAuthor.setText(editingItem.getAuthor());
    }

    @FXML
    private void onDeleteItemClick() {
        LibraryItem item = tblItems.getSelectionModel().getSelectedItem();
        if (item == null) {
            lblCollectionError.setText("Select item to delete first.");
            return;
        }
        lblCollectionError.setText("");

        try {
            ApplicationMain.getLibrary().delete(item);
            loadTableItems();
        } catch (BookNotFoundException ex) {
            lblCollectionError.setText(ex.getMessage());
        }
    }

    @FXML
    private void onLogoutClick() {
        try {
            ApplicationMain.loadView("login.fxml", new LoginController());
        } catch (IOException e) {
            System.err.println("Unable to logout (what?!)");
        }
    }
}

