package nl.inholland.konradfigura.finalassignment.Database;

import javafx.scene.control.Alert;
import nl.inholland.konradfigura.finalassignment.Model.User;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final String DATABASE_FILE = "data.db";

    private List<User> users = new ArrayList<>();
    private boolean hasUsersListBeenLoaded = false;

    public Database() {
        try {
            read();
        }
        catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Unable to load database file.");
        }

        //addUser("Gordon", "Freeman", LocalDate.of(1998, 11,19), "password1");
    }

    /**
     * Reads database from the DATABASE_FILE.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void read() throws IOException, ClassNotFoundException {
        File f = new File(DATABASE_FILE);
        if (f.exists() && !f.isDirectory()) {
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            users =  (List<User>)ois.readObject();
        }

        hasUsersListBeenLoaded = true;
    }

    public void addUser(String firstname, String lastname, LocalDate birthdate, String password) {
        User user = new User(firstname, lastname, birthdate, password);
        users.add(user);
    }

    private void addUser(User user) {
        users.add(user);
    }

    public User getUser(String username, String password) {
        for (User user : users) {
            if (user.getFirstName().equalsIgnoreCase(username.toLowerCase()) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public List<User> getAllUsers() {
        return users;
    }

    public int getUserId(User user) {
        for (int i = 0; i < users.size(); ++i) {
            if (user == users.get(i)) {
                return i + 1;
            }
        }
        return -1;
    }

    public void deleteUser(User user) {
        users.remove(user);
    }

    /**
     * Writes the database to the DATABASE_FILE.
     * @throws IOException
     */
    public void write() throws IOException {
        if (!hasUsersListBeenLoaded) {
            return;
        }

        FileOutputStream fos = new FileOutputStream(DATABASE_FILE);
        ObjectOutputStream obj = new ObjectOutputStream(fos);
        obj.writeObject(users);
        obj.close();
        fos.close();
    }

    public void editUser(User editingUser, String firstName, String lastName, String password, LocalDate birthdate) {
        int index = getUserId(editingUser) - 1;
        editingUser.setFirstName(firstName);
        editingUser.setLastName(lastName);
        editingUser.setPassword(password);
        editingUser.setBirthdate(birthdate);
        users.set(index, editingUser);
    }
}
