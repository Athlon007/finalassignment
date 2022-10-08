package nl.inholland.konradfigura.finalassignment.database;

import javafx.scene.control.Alert;
import nl.inholland.konradfigura.finalassignment.model.exceptions.MemberNotFoundException;
import nl.inholland.konradfigura.finalassignment.model.exceptions.UserAlreadyExists;
import nl.inholland.konradfigura.finalassignment.model.User;

public class UserDatabase extends Database<User>  {

    public UserDatabase() {
        super("users.db");
        try {
            this.list = read();
        }
        catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Unable to load database file.");
        }

        if (list.isEmpty()) {
            createUsers();
        }
    }

    public UserDatabase(String databaseFile) {
        super(databaseFile);
        try {
            this.list = read();
        }
        catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Unable to load database file.");
        }
    }

    private void createUsers() {
        // FOR DEBUG PURPOSES ONLY!
        // If the database gets loaded as empty (ex. file got corrupted, a dummy account will always be created).

        try {
            add("James", "password");
            add("Gordon", "hello");
        } catch (Exception e) {
            System.err.print("Wait, how did this happen?");
        }
        User user = list.get(0);
        User user2 = list.get(1);
        System.out.printf("A dummy account has been added.%nLogin: %s%nPassword: %s%n", user.getUsername(), user.getPassword());
        System.out.printf("A dummy account has been added.%nLogin: %s%nPassword: %s%n", user2.getUsername(), user2.getPassword());
    }

    @Override
    public void add(User member) {
        list.add(member);
    }

    public void add(String username, String password) throws IllegalArgumentException, UserAlreadyExists {
        if (existsUser(username)) {
            throw new UserAlreadyExists("Username with nickname " + username + " already exists.");
        }
        User user = new User(generateId(), username, password);
        add(user);
    }

    private boolean existsUser(String username) {
        for (User user : list) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }

        return false;
    }

    public User getUser(String username, String password) {
        for (User user : list) {
            if (user.getUsername().equalsIgnoreCase(username.toLowerCase()) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void delete(User user) throws MemberNotFoundException {
        if (!list.contains(user)) {
            throw new MemberNotFoundException("User was not found.");
        }
        list.remove(user);
    }

    @Override
    protected int generateId() {
        int highestId = 0;
        for (User user : getAll()) {
            if (user.getId() > highestId) {
                highestId = user.getId();
            }
        }

        return highestId + 1;
    }

    @Override
    public User getById(int id) {
        for (User user : list) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
}
