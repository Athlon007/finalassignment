module nl.inholland.konradfigura.finalassignment {
    requires javafx.controls;
    requires javafx.fxml;


    opens nl.inholland.konradfigura.finalassignment to javafx.fxml;
    exports nl.inholland.konradfigura.finalassignment;
}