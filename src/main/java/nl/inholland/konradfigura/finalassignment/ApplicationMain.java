package nl.inholland.konradfigura.finalassignment;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import nl.inholland.konradfigura.finalassignment.dataaccess.Database;
import nl.inholland.konradfigura.finalassignment.logic.Library;
import nl.inholland.konradfigura.finalassignment.logic.Members;
import nl.inholland.konradfigura.finalassignment.logic.Users;
import nl.inholland.konradfigura.finalassignment.model.Loadable;
import nl.inholland.konradfigura.finalassignment.ui.LoginController;

import java.awt.*;
import java.io.IOException;

public class ApplicationMain extends javafx.application.Application {
    private static Stage stage;

    private static Loadable[] loadables;


    @Override
    public void start(Stage stage) {
        ApplicationMain.stage = stage;
        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, this::onCloseHandler);

        Database database = new Database();

        loadables = new Loadable[] {
            new Users(),
            new Members(),
            new Library()
        };

        try {
            loadView("login.fxml", new LoginController());
        } catch (IOException e) {
            // Unlikely to happen, but even if the Login fails to load...
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Fatal Error");
            a.setHeaderText("Program was not able to start.");
            a.setContentText(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public static void loadView(String resource, Object controller) throws IOException {
        if (ApplicationMain.class.getResource(resource) == null) {
            throw new NullPointerException("Resource does not exist.");
        }
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource(resource));
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
        for (Loadable loadable : loadables) {
            loadable.save();
        }
    }
    /**
     * Returns the Users database.
     */
    public static Users getUsers() { return (Users)loadables[0]; }

    /**
     * Returns the Members database.
     */
    public static Members getMembers() { return (Members)loadables[1]; }

    /**
     * Returns the Library database.
     */
    public static Library getLibrary() { return (Library)loadables[2]; }

    /**
     * Returns the screen, where the mouse is located.
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