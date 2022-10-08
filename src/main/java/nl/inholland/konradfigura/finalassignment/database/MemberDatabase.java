package nl.inholland.konradfigura.finalassignment.database;

import javafx.scene.control.Alert;
import nl.inholland.konradfigura.finalassignment.model.exceptions.MemberNotFoundException;
import nl.inholland.konradfigura.finalassignment.model.Member;

import java.time.LocalDate;

public class MemberDatabase extends Database<Member>  {
    public MemberDatabase() {
        super("members.db");
        try {
            this.list = read();
        }
        catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Unable to load database file.");
        }
    }

    public MemberDatabase(String databaseFile) {
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

    @Override
    public void add(Member member) {
        list.add(member);
    }

    public void add(String firstname, String lastname, LocalDate birthdate) throws IllegalArgumentException {
        isInputValid(firstname, lastname, birthdate);

        Member member = new Member(generateId(), firstname, lastname, birthdate);
        add(member);
    }

    @Override
    public void delete(Member member) throws MemberNotFoundException {
        if (!list.contains(member)) {
            throw new MemberNotFoundException("User was not found.");
        }
        list.remove(member);
    }

    public void editUser(Member editingMember, String firstname, String lastname, LocalDate birthdate)
            throws MemberNotFoundException {
        isInputValid(firstname, lastname, birthdate);

        if (!list.contains(editingMember)) {
            throw new MemberNotFoundException(String.format("User '%s %s' was not found.", firstname, lastname));
        }

        int index = getItemPositonWithinList(editingMember);
        editingMember.setFirstName(firstname);
        editingMember.setLastName(lastname);
        editingMember.setBirthdate(birthdate);
        list.set(index, editingMember);
    }

    private void isInputValid(String firstname, String lastname, LocalDate birthdate) {
        String errors = "";
        if (firstname.isEmpty()) {
            errors += "First name missing\n";
        }
        if (lastname.isEmpty()) {
            errors += "Last name missing\n";
        }
        if (birthdate == null) {
            errors += "Birth date missing\n";
        }
        if (errors.length() > 0) {
            throw new IllegalArgumentException(errors);
        }
    }

    @Override
    protected int generateId() {
        int highestId = 0;
        for (Member member : getAll()) {
            if (member.getId() > highestId) {
                highestId = member.getId();
            }
        }

        return highestId + 1;
    }

    @Override
    public Member getById(int id) {
        for (Member member : list) {
            if (member.getId() == id) {
                return member;
            }
        }
        return null;
    }
}
