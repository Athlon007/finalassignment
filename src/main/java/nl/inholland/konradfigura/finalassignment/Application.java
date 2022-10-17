package nl.inholland.konradfigura.finalassignment;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import nl.inholland.konradfigura.finalassignment.logic.Database;
import nl.inholland.konradfigura.finalassignment.logic.LibraryDatabase;
import nl.inholland.konradfigura.finalassignment.logic.MemberDatabase;
import nl.inholland.konradfigura.finalassignment.logic.UserDatabase;
import nl.inholland.konradfigura.finalassignment.ui.LoginController;

import java.awt.*;
import java.io.IOException;

public class Application extends javafx.application.Application {
    private static Stage stage;

    private static Database[] databases;

    @Override
    public void start(Stage stage) {
        Application.stage = stage;
        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, this::onCloseHandler);

        databases = new Database[] {
                new UserDatabase(),
                new MemberDatabase(),
                new LibraryDatabase()
        };

        try {
            loadView("login.fxml", new LoginController());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public static void loadView(String resource, Object controller) throws IOException {
        if (Application.class.getResource(resource) == null) {
            throw new NullPointerException("Resource does not exist.");
        }
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(resource));
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Library System");
        stage.setScene(scene);
        stage.show();

        // Center the view to the currently active display
        Rectangle2D rect = getActiveScreen().getVisualBounds();
        stage.setX(rect.getMinX() + (rect.getWidth() - stage.getWidth()) / 2);
        stage.setY(rect.getMinY() + (rect.getHeight() - stage.getHeight()) / 2);
    }

    private void onCloseHandler(WindowEvent event) {
        try {
            for (Database db : databases) {
                db.write();
            }
        }
        catch (Exception ex ){
            System.err.println(ex.getMessage());
        }
    }

    public static UserDatabase getUsers() {
        return (UserDatabase)databases[0];
    }

    public static MemberDatabase getMembers() {
        return (MemberDatabase)databases[1];
    }

    public static LibraryDatabase getLibrary() { return (LibraryDatabase)databases[2]; }

    /**
     * Returns the screen, on which currently is mouse.
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