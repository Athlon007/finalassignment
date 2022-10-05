package nl.inholland.konradfigura.finalassignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import nl.inholland.konradfigura.finalassignment.Database.UserDatabase;
import nl.inholland.konradfigura.finalassignment.Model.User;
import nl.inholland.konradfigura.finalassignment.Model.UserLoadable;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage stage;

    private static UserDatabase db;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, this::onCloseHandler);
        db = new UserDatabase();
        loadView(Views.LOGIN);
    }

    public static void main(String[] args) {
        launch();
    }

    public static void loadView(Views view) throws IOException {
        loadView(view, null);
    }

    public static void loadView(Views view, User asUser) throws IOException {
        String resourceName = "";
        switch (view) {
            case LOGIN -> resourceName = "hello-view.fxml";
            case DASHBOARD -> resourceName = "dashboard.fxml";
            default -> new UnsupportedOperationException("View " + view + " does not exist.");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(resourceName));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Library System");
        stage.setScene(scene);
        stage.show();

        if (fxmlLoader.getController() instanceof UserLoadable) {
           ((UserLoadable) fxmlLoader.getController()).loadUser(asUser);
        }

        // Center the view.
        Rectangle2D rect = Screen.getPrimary().getVisualBounds();
        stage.setX((rect.getWidth() - stage.getWidth()) / 2);
        stage.setY((rect.getHeight() - stage.getHeight()) / 2);
    }

    public static UserDatabase getDatabase() {
        return db;
    }

    private void onCloseHandler(WindowEvent event) {
        try {
            db.write();
        }
        catch (Exception ex ){ }
    }
}