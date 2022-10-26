package nl.inholland.konradfigura.finalassignment.logic;

import nl.inholland.konradfigura.finalassignment.dataaccess.Database;
import nl.inholland.konradfigura.finalassignment.model.LendInfo;
import nl.inholland.konradfigura.finalassignment.model.LibraryItem;
import nl.inholland.konradfigura.finalassignment.model.Loadable;
import nl.inholland.konradfigura.finalassignment.model.Member;
import nl.inholland.konradfigura.finalassignment.model.exceptions.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Library implements Loadable<LibraryItem> {
    public static final int OVERTIME_DAYS = 21;

    private String databaseFile = "library.db";

    private Database<LibraryItem> database = new Database<>();

    private List<LibraryItem> list;

    public Library() {
        load(database.read(databaseFile));
    }

    public Library(String databaseFile) {
        this.databaseFile = databaseFile;
        load(database.read(databaseFile));
    }

    @Override
    public void load(List<LibraryItem> list) {
        this.list = list;
    }

    @Override
    public void save() {
        try {
            database.write(databaseFile, getAll());
        } catch (IOException e) {
            System.out.println("Unable to save Library database.");
        }
    }

    public List<LibraryItem> getAll() {
        return list;
    }

    public void add(String title, String author) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        if (!isInputValid(sb, title, author)) {
            throw new IllegalArgumentException(sb.toString());
        }

        LibraryItem item = new LibraryItem(generateId(), title, author);
        list.add(item);
    }

    public void delete(LibraryItem obj) throws BookNotFoundException {
        if (!list.contains(obj)) {
            throw new BookNotFoundException("Book is not in the database.");
        }
        if (!obj.isAvailable()) {
            throw new BookNotFoundException("Book is not available");
        }

        list.remove(obj);
    }

    protected int generateId() {
        if (list == null || list.isEmpty()) {
            return 1;
        }

        int highestId = 0;
        for (LibraryItem item : list) {
            if (item.getId() > highestId) {
                highestId = item.getId();
            }
        }
        return highestId + 1;
    }

    public LibraryItem getById(int id) {
        for (LibraryItem item : list) {
            if (item.getId() == id) {
                return item;
            }
        }

        return null;
    }

    public void lendBook(LibraryItem book, Member member, LocalDate date)
            throws BookNotFoundException, MemberNotFoundException, BookNotAvailableException {
        if (!list.contains(book)) {
            throw new BookNotFoundException("Book is not registered in the database.");
        }

        if (member == null) {
            throw new MemberNotFoundException("Member was not found.");
        }

        if (!book.isAvailable()) {
            throw new BookNotAvailableException("Book is already borrowed.");
        }

        book.lend(member, date);
    }

    public void returnBook(LibraryItem book) throws BookNotFoundException, OvertimeException, BookNotBorrowedException {
        if (!list.contains(book)) {
            throw new BookNotFoundException("Book is not registered in the database.");
        }

        final LendInfo info = book.returnItem();

        if (info == null) {
            throw new BookNotBorrowedException("Item is not borrowed.");
        }

        final long overdueDays = ChronoUnit.DAYS.between(info.date(), LocalDate.now());
        if (overdueDays > OVERTIME_DAYS) {
            throw new OvertimeException(String.format("Book was returned %d days overtime.", overdueDays));
        }
    }

    public void edit(LibraryItem book, String title, String author) throws IllegalArgumentException, BookNotFoundException, BookNotAvailableException {
        StringBuilder sb = new StringBuilder();
        if (!isInputValid(sb, title, author)) {
            throw new IllegalArgumentException(sb.toString());
        }

        if (!list.contains(book)) {
            throw new BookNotFoundException("Book is not in the database.");
        }

        if (!book.isAvailable()) {
            throw new BookNotAvailableException("Book has been lent, and can't be edited or deleted.");
        }

        book.setTitle(title);
        book.setAuthor(author);
    }

    public List<LibraryItem> getAll(String query) {
        List<LibraryItem> result = new ArrayList<>();
        for (LibraryItem item : list) {
            if (item.getTitle().toUpperCase().contains(query.toUpperCase())
                    || item.getAuthor().toUpperCase().contains(query.toUpperCase())) {
                result.add(item);
            }
        }
        return result;
    }

    public boolean isMemberBorrowingBook(Member member) {
        for (LibraryItem li : list) {
            if (li.getLendingUser() != null && li.getLendingUser().equals(member)) {
                return true;
            }
        }

        return false;
    }

    private boolean isInputValid(StringBuilder sb, String title, String author) {
        if (title.isEmpty()) {
            sb.append("Title is empty.\n");
        }

        if (author.isEmpty()) {
            sb.append("Author is empty.\n");
        }

        return sb.length() == 0;
    }
}
