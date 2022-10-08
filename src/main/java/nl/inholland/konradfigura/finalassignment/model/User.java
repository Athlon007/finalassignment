package nl.inholland.konradfigura.finalassignment.model;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private final String username;
    private final String password;

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
}
