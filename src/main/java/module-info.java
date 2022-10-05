module nl.inholland.konradfigura.finalassignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens nl.inholland.konradfigura.finalassignment to javafx.fxml;
    exports nl.inholland.konradfigura.finalassignment;
    exports nl.inholland.konradfigura.finalassignment.Database;
    exports nl.inholland.konradfigura.finalassignment.Model;
}