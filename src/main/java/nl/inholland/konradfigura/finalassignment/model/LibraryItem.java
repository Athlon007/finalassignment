package nl.inholland.konradfigura.finalassignment.model;

import java.io.Serializable;
import java.time.LocalDate;

public class LibraryItem implements Serializable {
    private int id;
    private String title;
    private String author;
    private LendInfo lendInfo;

    public LibraryItem(int id, boolean isAvailable, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int itemID) {
        this.id = itemID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return lendInfo == null;
    }

    public String isAvailableHumanReadable() {
        return isAvailable() ? "Yes" : "No";
    }

    public void lend(Member lender, LocalDate date) {
        lendInfo = new LendInfo(lender, date);
    }

    public LendInfo getLendInfo() { return lendInfo; }

    public LendInfo returnItem() {
        LendInfo info = lendInfo;
        lendInfo = null;
        return info;
    }
}
