package nl.inholland.konradfigura.finalassignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import nl.inholland.konradfigura.finalassignment.Database.LibraryDatabase;
import nl.inholland.konradfigura.finalassignment.Database.UserDatabase;
import nl.inholland.konradfigura.finalassignment.Model.User;
import nl.inholland.konradfigura.finalassignment.Model.UserLoadable;

import java.awt.*;
import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage stage;

    private static UserDatabase db;
    private static LibraryDatabase library;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, this::onCloseHandler);
        db = new UserDatabase();
        library = new LibraryDatabase();
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

        // Center the view to the currently active display
        Rectangle2D rect = getActiveScreen().getVisualBounds();
        stage.setX(rect.getMinX() + (rect.getWidth() - stage.getWidth()) / 2);
        stage.setY(rect.getMinY() + (rect.getHeight() - stage.getHeight()) / 2);
    }

    public static UserDatabase getDatabase() {
        return db;
    }

    private void onCloseHandler(WindowEvent event) {
        try {
            db.write();
            library.write();
        }
        catch (Exception ex ){ }
    }

    public static LibraryDatabase getLibrary() { return library; }

    /**
     * Returns the screen, on which currently is mouse.
     * @return
     */
    private static Screen getActiveScreen() {
        Point mousePosition = MouseInfo.getPointerInfo().getLocation();
        Screen current = Screen.getPrimary();
        for (Screen screen : Screen.getScreens()) {
            if (mousePosition.getX() > screen.getVisualBounds().getMinX()
                    && mousePosition.getX() < screen.getVisualBounds().getMaxX()
                    && mousePosition.getY() > screen.getVisualBounds().getMinY()
                    && mousePosition.getY() < screen.getVisualBounds().getMaxY()) {
                current = screen;
            }
        }
        return current;
    }
}