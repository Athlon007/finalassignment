package nl.inholland.konradfigura.finalassignment.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Member implements Serializable {
    private int id;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;

    public Member(int id, String firstName, String lastName, LocalDate birthdate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String value) { firstName = value; }

    public String getLastName() { return lastName; }
    public void setLastName(String value) { lastName = value; }

    public LocalDate getBirthdate() { return birthdate; }
    public void setBirthdate(LocalDate date) { birthdate = date; }

    public int getId() {
        return id;
    }

    public void setId(int value) {
        id = value;
    }
}
