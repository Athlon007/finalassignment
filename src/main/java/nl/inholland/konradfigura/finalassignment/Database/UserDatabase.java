package nl.inholland.konradfigura.finalassignment.Database;

import javafx.scene.control.Alert;
import nl.inholland.konradfigura.finalassignment.Model.Exceptions.MemberNotFoundException;
import nl.inholland.konradfigura.finalassignment.Model.User;

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

        createUsers();
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
        if (list.size() == 0) {
            add("user1", "password");
            add("user2", "hello");
            User user = list.get(0);
            User user2 = list.get(1);
            System.out.println(String.format("A dummy account has been added.\nLogin: %s\nPassword: %s", user.getUsername(), user.getPassword()));
            System.out.println(String.format("A dummy account has been added.\nLogin: %s\nPassword: %s", user2.getUsername(), user2.getPassword()));
        }
    }

    @Override
    public void add(User member) {
        list.add(member);
    }

    public void add(String username, String password) throws IllegalArgumentException {
        User user = new User(generateId(), username, password);
        add(user);
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
