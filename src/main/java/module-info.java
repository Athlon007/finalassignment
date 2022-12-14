module nl.inholland.konradfigura.finalassignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens nl.inholland.konradfigura.finalassignment to javafx.fxml;
    exports nl.inholland.konradfigura.finalassignment;
    exports nl.inholland.konradfigura.finalassignment.logic;
    exports nl.inholland.konradfigura.finalassignment.model;
    exports nl.inholland.konradfigura.finalassignment.model.exceptions;
    exports nl.inholland.konradfigura.finalassignment.ui;
    exports nl.inholland.konradfigura.finalassignment.ui.customcontrols;
    opens nl.inholland.konradfigura.finalassignment.ui to javafx.fxml;
    exports nl.inholland.konradfigura.finalassignment.dataaccess;
}