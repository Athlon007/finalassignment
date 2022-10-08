import junit.framework.Assert;
import nl.inholland.konradfigura.finalassignment.Database.LibraryDatabase;
import nl.inholland.konradfigura.finalassignment.Database.MemberDatabase;
import nl.inholland.konradfigura.finalassignment.Database.UserDatabase;
import nl.inholland.konradfigura.finalassignment.Model.Exceptions.BookNotAvailableException;
import nl.inholland.konradfigura.finalassignment.Model.Exceptions.BookNotFoundException;
import nl.inholland.konradfigura.finalassignment.Model.Exceptions.MemberNotFoundException;
import nl.inholland.konradfigura.finalassignment.Model.Exceptions.OvertimeException;
import nl.inholland.konradfigura.finalassignment.Model.LibraryItem;
import nl.inholland.konradfigura.finalassignment.Model.Member;
import nl.inholland.konradfigura.finalassignment.Model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Duration;
import java.time.LocalDate;

public class TestDatabase {
    private final String LIBRARY_TEST_FILE = "testlib.db";
    private final String USER_TEST_FILE = "testusr.db";
    private final String MEMBER_TEST_FILE = "testmem.db";

    private LibraryDatabase dbLibrary;
    private UserDatabase dbUsers;
    private MemberDatabase dbMembers;

    public TestDatabase() {
        File libFile = new File(LIBRARY_TEST_FILE);
        if (libFile.exists()) {
            libFile.delete();
        }

        File usrFile = new File(USER_TEST_FILE);
        if (usrFile.exists()) {
            usrFile.delete();
        }

        File memFile = new File(MEMBER_TEST_FILE);
        if (memFile.exists()) {
            memFile.delete();
        }

        dbLibrary = new LibraryDatabase(LIBRARY_TEST_FILE);
        dbMembers = new MemberDatabase(MEMBER_TEST_FILE);
        dbUsers = new UserDatabase(USER_TEST_FILE);
    }
    @Test
    public void testOvertimeBookReturn() {
        LocalDate today = LocalDate.now();
        LocalDate lendDay = LocalDate.of(2022, 9, 1);
        long days = Duration.between(lendDay.atStartOfDay(), today.atStartOfDay()).toDays() - dbLibrary.OVERTIME_DAYS;
        dbLibrary.add("Book", "Author");
        dbMembers.add("Member", "One", LocalDate.now());
        Member member = dbMembers.getAll().get(0);
        LibraryItem book = dbLibrary.getAll().get(0);
        try {
            dbLibrary.lendBook(book, member, lendDay);
            dbLibrary.returnBook(book);
        }  catch (OvertimeException e) {
            Assertions.assertEquals(String.format("Book was returned %d days overtime.", days), e.getMessage());
        } catch (Exception e) {
        }
        try {
            dbLibrary.delete(book);
            dbMembers.delete(member);
        }
        catch (Exception e) {}
    }

    @Test
    public void testUserLogin() {
        String username = "user";
        String password = "password";
        dbUsers.add(username, password);

        User user = dbUsers.getUser(username, password);
        Assertions.assertEquals("user", user.getUsername());

        try {
            dbUsers.delete(user);
        } catch (Exception e) {}
    }
}
