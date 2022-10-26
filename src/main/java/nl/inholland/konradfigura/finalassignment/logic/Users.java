package nl.inholland.konradfigura.finalassignment.logic;

import nl.inholland.konradfigura.finalassignment.dataaccess.Database;
import nl.inholland.konradfigura.finalassignment.model.Loadable;
import nl.inholland.konradfigura.finalassignment.model.User;
import nl.inholland.konradfigura.finalassignment.model.exceptions.MemberNotFoundException;
import nl.inholland.konradfigura.finalassignment.model.exceptions.UserAlreadyExists;

import java.io.IOException;
import java.util.List;

public class Users implements Loadable<User> {
    private List<User> list;

    private String databaseFile = "users.db";
    private Database<User> database = new Database<>();


    public Users() {
        load(database.read(databaseFile));
    }

    public Users(String databaseFile) {
        this.databaseFile = databaseFile;
        load(database.read(databaseFile));
    }

    @Override
    public void load(List<User> list) {
        this.list = list;
        if (list.isEmpty()) {
            createUsers();
        }
    }

    @Override
    public void save() {
        try {
            database.write(databaseFile, getAll());
        } catch (IOException e) {
            System.out.println("Unable to write to Users database.");
        }
    }

    public List<User> getAll() {
        return list;
    }

    private void createUsers() {
        // FOR DEBUG PURPOSES ONLY!
        // If the database gets loaded as empty (ex. file got corrupted, a dummy account will always be created).

        try {
            add("UserOne", "password");
            add("UserTwo", "hello");
        } catch (Exception e) {
            System.err.print("Wait, how did this happen?\n" + e.getMessage());
        }
        User user = list.get(0);
        User user2 = list.get(1);
        System.out.printf("A dummy account has been added.%nLogin: %s%nPassword: %s%n", user.getUsername(), user.getPassword());
        System.out.printf("A dummy account has been added.%nLogin: %s%nPassword: %s%n", user2.getUsername(), user2.getPassword());
    }

    public void add(String username, String password) throws IllegalArgumentException, UserAlreadyExists {
        if (existsUser(username)) {
            throw new UserAlreadyExists("Username with nickname " + username + " already exists.");
        }
        User user = new User(generateId(), username, password);
        list.add(user);
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

    public void delete(User user) throws MemberNotFoundException {
        if (!list.contains(user)) {
            throw new MemberNotFoundException("User was not found.");
        }
        list.remove(user);
    }

    protected int generateId() {
        int highestId = 0;
        for (User user : getAll()) {
            if (user.getId() > highestId) {
                highestId = user.getId();
            }
        }

        return highestId + 1;
    }
}
