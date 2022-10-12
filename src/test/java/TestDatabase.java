import nl.inholland.konradfigura.finalassignment.database.LibraryDatabase;
import nl.inholland.konradfigura.finalassignment.database.MemberDatabase;
import nl.inholland.konradfigura.finalassignment.database.UserDatabase;
import nl.inholland.konradfigura.finalassignment.model.exceptions.OvertimeException;
import nl.inholland.konradfigura.finalassignment.model.LibraryItem;
import nl.inholland.konradfigura.finalassignment.model.Member;
import nl.inholland.konradfigura.finalassignment.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Duration;
import java.time.LocalDate;

class TestDatabase {

    private final LibraryDatabase dbLibrary;
    private final UserDatabase dbUsers;
    private final MemberDatabase dbMembers;

    public TestDatabase() {
        String LIBRARY_TEST_FILE = "testlib.db";
        File libFile = new File(LIBRARY_TEST_FILE);
        if (libFile.exists()) {
            libFile.delete();
        }

        String USER_TEST_FILE = "testusr.db";
        File usrFile = new File(USER_TEST_FILE);
        if (usrFile.exists()) {
            usrFile.delete();
        }

        String MEMBER_TEST_FILE = "testmem.db";
        File memFile = new File(MEMBER_TEST_FILE);
        if (memFile.exists()) {
            memFile.delete();
        }

        dbLibrary = new LibraryDatabase(LIBRARY_TEST_FILE);
        dbMembers = new MemberDatabase(MEMBER_TEST_FILE);
        dbUsers = new UserDatabase(USER_TEST_FILE);
    }
    @Test
    void testOvertimeBookReturn() {
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
    void testUserLogin() {
        String username = "user";
        String password = "password";
        try {
            dbUsers.add(username, password);
        } catch (Exception e) {}

        User user = dbUsers.getUser(username, password);
        Assertions.assertEquals("user", user.getUsername());

        try {
            dbUsers.delete(user);
        } catch (Exception e) {}
    }
}
