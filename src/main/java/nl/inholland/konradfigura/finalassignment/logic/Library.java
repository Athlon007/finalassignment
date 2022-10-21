package nl.inholland.konradfigura.finalassignment.logic;

import nl.inholland.konradfigura.finalassignment.model.LendInfo;
import nl.inholland.konradfigura.finalassignment.model.LibraryItem;
import nl.inholland.konradfigura.finalassignment.model.Loadable;
import nl.inholland.konradfigura.finalassignment.model.Member;
import nl.inholland.konradfigura.finalassignment.model.exceptions.BookNotAvailableException;
import nl.inholland.konradfigura.finalassignment.model.exceptions.BookNotFoundException;
import nl.inholland.konradfigura.finalassignment.model.exceptions.MemberNotFoundException;
import nl.inholland.konradfigura.finalassignment.model.exceptions.OvertimeException;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Library implements Loadable<LibraryItem> {
    public static final int OVERTIME_DAYS = 21;

    private List<LibraryItem> list;

    public Library() {}

    public Library(List<LibraryItem> list) {
        setAll(list);
    }

    @Override
    public void setAll(List<LibraryItem> list) {
        this.list = list;
    }

    @Override
    public List<LibraryItem> getAll() {
        return list;
    }

    public void add(String title, String author) throws NullPointerException {
        if (title.isEmpty()) {
            throw new NullPointerException("Title is empty.");
        }
        if (author.isEmpty()) {
            throw new NullPointerException("Author is empty.");
        }

        LibraryItem item = new LibraryItem(generateId(), title, author);
        add(item);
    }

    public void add(LibraryItem obj) {
        list.add(obj);
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

    public void returnBook(LibraryItem book) throws BookNotFoundException, OvertimeException, NullPointerException {
        if (!list.contains(book)) {
            throw new BookNotFoundException("Book is not registered in the database.");
        }

        final LendInfo info = book.returnItem();

        if (info == null) {
            throw new NullPointerException("Item is not borrowed.");
        }

        final LocalDate lendDatePlusOvertime = info.date().plusDays(OVERTIME_DAYS);
        if (LocalDate.now().isAfter(lendDatePlusOvertime)) {
            long days = Duration.between(lendDatePlusOvertime.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays();
            throw new OvertimeException(String.format("Book was returned %d days overtime.", days));
        }
    }

    public void edit(LibraryItem book, String title, String author) throws IllegalArgumentException, BookNotFoundException, BookNotAvailableException {
        if (title.isEmpty()) {
            throw new IllegalArgumentException("Title is empty.");
        }

        if (author.isEmpty()) {
            throw new IllegalArgumentException("Author is empty.");
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

    public boolean isMemberBorrowingBook(Member member) throws NullPointerException {
        for (LibraryItem li : list) {
            if (li.getLendingUser() != null && li.getLendingUser().equals(member)) {
                return true;
            }
        }

        return false;
    }
}
