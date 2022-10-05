package nl.inholland.konradfigura.finalassignment.Database;

import javafx.scene.control.Alert;
import nl.inholland.konradfigura.finalassignment.Model.User;

import java.time.LocalDate;
import java.util.List;

public class UserDatabase extends Database<User>  {
    // TODO: Make an abstract version of Database object, then inherit it here.

    @Override
    public String getDatabaseFile() { return "data.db"; }

    public UserDatabase() {
        try {
            this.list = read();
        }
        catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Unable to load database file.");
        }

        // FOR DEBUG PURPOSES ONLY!
        // If the database gets loaded as empty (ex. file got corrupted, a dummy account will always be created).
        if (list.size() == 0) {
            add("Gordon", "Freeman", LocalDate.of(1998, 11, 19), "password1");
            User user = list.get(0);
            System.out.println(String.format("A dummy account has been added.\nLogin: %s\nPassword: %s", user.getFirstName(), user.getPassword()));
        }
    }

    @Override
    public void add(User user) {
        list.add(user);
    }

    public void add(String firstname, String lastname, LocalDate birthdate, String password) {
        User user = new User(generateId(), firstname, lastname, birthdate, password);
        add(user);
    }

    public User getUser(String username, String password) {
        for (User user : list) {
            if (user.getFirstName().equalsIgnoreCase(username.toLowerCase()) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void delete(User user) {
        if (list.contains(user)) {
            list.remove(user);
        }
    }

    public void editUser(User editingUser, String firstName, String lastName, String password, LocalDate birthdate) {
        if (!list.contains(editingUser)) {
            // TODO: Should throw an exception instead...
            return;
        }

        int index = getItemPositonWithinList(editingUser);
        editingUser.setFirstName(firstName);
        editingUser.setLastName(lastName);
        editingUser.setPassword(password);
        editingUser.setBirthdate(birthdate);
        list.set(index, editingUser);
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
}
