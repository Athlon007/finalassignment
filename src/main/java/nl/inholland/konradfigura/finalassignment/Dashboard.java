package nl.inholland.konradfigura.finalassignment;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabContainer.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, tab, t1) -> {
                    // TODO: Implement tab changing actions.
                }
        );
        tabContainer.getSelectionModel().select(tabLending);
    }

    @FXML
    protected void onAddMemberClick(ActionEvent event) {
        paneAddMember.setVisible(true);
        // TODO: Clean text fields.
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
}

