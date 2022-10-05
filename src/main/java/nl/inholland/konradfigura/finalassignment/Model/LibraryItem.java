package nl.inholland.konradfigura.finalassignment.Model;

import java.io.Serializable;

public class LibraryItem implements Serializable {
    private int itemID;
    private boolean isAvailable;
    private String title;
    private String author;

    public LibraryItem(int itemID, boolean isAvailable, String title, String author) {
        this.itemID = itemID;
        this.isAvailable = isAvailable;
        this.title = title;
        this.author = author;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
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
