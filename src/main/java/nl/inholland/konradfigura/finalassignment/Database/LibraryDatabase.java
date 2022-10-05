package nl.inholland.konradfigura.finalassignment.Database;

import javafx.scene.control.Alert;
import nl.inholland.konradfigura.finalassignment.Model.LibraryItem;

import java.util.List;

public class LibraryDatabase extends Database<LibraryItem> {

    public LibraryDatabase() {
        try {
            this.list = read();
        }
        catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Unable to load library database file.");
        }
    }

    public void add(String title, String author) {
        LibraryItem item = new LibraryItem(generateId(), true, title, author);
        add(item);
    }

    @Override
    public void add(LibraryItem obj) {
        list.add(obj);
    }

    @Override
    public void delete(LibraryItem obj) {
        if (list.contains(obj)) {
            list.remove(obj);
        }
    }

    @Override
    public String getDatabaseFile() {
        return "library.db";
    }

    @Override
    protected int generateId() {
        if (list == null || list.size() == 0) {
            return 1;
        }

        int highestId = 0;
        for (LibraryItem item : list) {
            if (item.getId() > highestId) {
                highestId = item.getId();
            }
        }
        return highestId + 1;
    }
}
