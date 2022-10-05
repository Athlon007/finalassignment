package nl.inholland.konradfigura.finalassignment.Model;

import java.io.Serializable;
import java.time.LocalDate;

public class User implements Serializable {
    private int id;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private String password;

    public User(int id, String firstName, String lastName, LocalDate birthdate, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String value) { firstName = value; }

    public String getLastName() { return lastName; }
    public void setLastName(String value) { lastName = value; }

    public LocalDate getBirthdate() { return birthdate; }
    public void setBirthdate(LocalDate date) { birthdate = date; }

    public String getPassword() {
        return password;
    }
    public void setPassword(String value) { password = value; }

    public int getId() {
        return id;
    }

    public void setId(int value) {
        id = value;
    }
}
