module nl.inholland.konradfigura.finalassignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens nl.inholland.konradfigura.finalassignment to javafx.fxml;
    exports nl.inholland.konradfigura.finalassignment;
    exports nl.inholland.konradfigura.finalassignment.database;
    exports nl.inholland.konradfigura.finalassignment.model;
    exports nl.inholland.konradfigura.finalassignment.model.exceptions;
}