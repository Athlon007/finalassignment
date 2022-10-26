package nl.inholland.konradfigura.finalassignment.logic;

import nl.inholland.konradfigura.finalassignment.dataaccess.Database;
import nl.inholland.konradfigura.finalassignment.model.Loadable;
import nl.inholland.konradfigura.finalassignment.model.exceptions.MemberNotFoundException;
import nl.inholland.konradfigura.finalassignment.model.Member;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Members implements Loadable<Member> {
    private List<Member> list;

    private String databaseFile = "members.db";

    private Database<Member> database = new Database<>();

    public Members() {
        load(database.read(databaseFile));
    }

    public Members(String databaseFile) {
        this.databaseFile = databaseFile;
        load(database.read(databaseFile));
    }

    @Override
    public void load(List<Member> list) {
        this.list = list;
    }

    @Override
    public void save() {
        try {
            database.write(databaseFile, getAll());
        } catch (IOException e) {
            System.out.println("Unable to write Members database.");
        }
    }

    public List<Member> getAll() {
        return list;
    }

    public void add(String firstname, String lastname, LocalDate birthdate) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        if (!isInputValid(sb, firstname, lastname, birthdate)) {
            throw new IllegalArgumentException(sb.toString());
        }

        Member member = new Member(generateId(), firstname, lastname, birthdate);
        list.add(member);
    }

    public void delete(Member member) throws MemberNotFoundException {
        if (!list.contains(member)) {
            throw new MemberNotFoundException("User was not found.");
        }
        list.remove(member);
    }

    public void editUser(Member editingMember, String firstname, String lastname, LocalDate birthdate)
            throws MemberNotFoundException {
        StringBuilder sb = new StringBuilder();
        if (!isInputValid(sb, firstname, lastname, birthdate)) {
            throw new IllegalArgumentException(sb.toString());
        }

        if (!list.contains(editingMember)) {
            throw new MemberNotFoundException(String.format("User '%s %s' was not found.", firstname, lastname));
        }

        editingMember.setFirstName(firstname);
        editingMember.setLastName(lastname);
        editingMember.setBirthdate(birthdate);
    }

    private boolean isInputValid(StringBuilder sb, String firstname, String lastname, LocalDate birthdate) {
        if (firstname.isEmpty()) {
            sb.append("First name missing\n");
        }
        if (lastname.isEmpty()) {
            sb.append("Last name missing\n");
        }
        if (birthdate == null) {
            sb.append("Birth date missing\n");
        }

        return sb.length() == 0;
    }

    protected int generateId() {
        int highestId = 0;
        for (Member member : getAll()) {
            if (member.getId() > highestId) {
                highestId = member.getId();
            }
        }

        return highestId + 1;
    }

    public Member getById(int id) {
        for (Member member : list) {
            if (member.getId() == id) {
                return member;
            }
        }
        return null;
    }

    public List<Member> getAll(String query) {
        List<Member> result = new ArrayList<>();
        for (Member member : list) {
            if (member.getFirstName().toUpperCase().contains(query.toUpperCase())
                    || member.getLastName().toUpperCase().contains(query.toUpperCase())) {
                result.add(member);
            }
        }
        return result;
    }
}
