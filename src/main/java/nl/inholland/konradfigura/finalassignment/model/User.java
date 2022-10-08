package nl.inholland.konradfigura.finalassignment.model;

import java.io.Serializable;
import java.time.LocalDate;

public class User implements Serializable {

    private int id;
    private String username;
    private LocalDate birthdate;
    private String password;

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() { return username; }

    public String getPassword() {
        return password;
    }
    public void setPassword(String value) { password = value; }
}
