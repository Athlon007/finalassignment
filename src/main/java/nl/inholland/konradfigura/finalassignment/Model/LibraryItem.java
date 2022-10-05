package nl.inholland.konradfigura.finalassignment.Model;

import java.io.Serializable;

public class LibraryItem implements Serializable {
    private int id;
    private boolean isAvailable;
    private String title;
    private String author;

    public LibraryItem(int id, boolean isAvailable, String title, String author) {
        this.id = id;
        this.isAvailable = isAvailable;
        this.title = title;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int itemID) {
        this.id = itemID;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
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
}
