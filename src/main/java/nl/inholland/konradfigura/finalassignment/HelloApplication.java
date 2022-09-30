package nl.inholland.konradfigura.finalassignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static HelloApplication instance;

    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        instance = this;
        this.stage = stage;

        loadView(Views.Login);
    }

    public static void main(String[] args) {
        launch();
    }

    public static HelloApplication getInstance() {
        return instance;
    }

    public void loadView(Views view) throws IOException {
        String resourceName = "";
        switch (view) {
            case Login -> resourceName = "hello-view.fxml";
            case Dashboard -> resourceName = "dashboard.fxml";
            default -> new UnsupportedOperationException("View " + view + " does not exist.");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(resourceName));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Library System");
        stage.setScene(scene);
        stage.show();
    }
}