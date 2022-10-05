package nl.inholland.konradfigura.finalassignment.Database;

import javafx.scene.control.Alert;
import nl.inholland.konradfigura.finalassignment.Model.User;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
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

        //addUser("Gordon", "Freeman", LocalDate.of(1998, 11,19), "password1");
    }

    @Override
    public void add(User user) {
        list.add(user);
    }

    public void add(String firstname, String lastname, LocalDate birthdate, String password) {
        User user = new User(firstname, lastname, birthdate, password);
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
    public List<User> getAll() {
        return list;
    }

    @Override
    public int getId(User user) {
        for (int i = 0; i < list.size(); ++i) {
            if (user == list.get(i)) {
                return i + 1;
            }
        }
        return -1;
    }

    @Override
    public void delete(User user) {
        list.remove(user);
    }

    public void editUser(User editingUser, String firstName, String lastName, String password, LocalDate birthdate) {
        int index = getId(editingUser) - 1;
        editingUser.setFirstName(firstName);
        editingUser.setLastName(lastName);
        editingUser.setPassword(password);
        editingUser.setBirthdate(birthdate);
        list.set(index, editingUser);
    }
}
