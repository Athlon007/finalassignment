package nl.inholland.konradfigura.finalassignment;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabContainer.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, tab, t1) -> {
                    // TODO: Implement tab changing actions.
                }
        );
        tabContainer.getSelectionModel().select(tabLending);

        // Initialize columns for tblItems.
        //initItemsTableView();
    }

    private void initItemsTableView() {
        TableColumn id = new TableColumn("Item Code");
        // Small workaround, so the initial size of Item Code and Available headers is smaller.
        id.setPrefWidth(100);
        id.setMaxWidth(100);
        id.setMinWidth(100);
        TableColumn available = new TableColumn("Available");
        available.setPrefWidth(100);
        available.setMaxWidth(100);
        available.setMinWidth(100);
        TableColumn title = new TableColumn("Title");
        TableColumn author = new TableColumn("Author");
        tblItems.getColumns().addAll(id, available, title, author);

        // Setting it back, so we can still resize Item Code and Available.
        id.setMaxWidth(10000);
        id.setMinWidth(1);
        available.setMaxWidth(10000);
        available.setMinWidth(1);
    }
}

