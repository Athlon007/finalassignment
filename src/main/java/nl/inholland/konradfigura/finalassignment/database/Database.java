package nl.inholland.konradfigura.finalassignment.database;

import nl.inholland.konradfigura.finalassignment.model.exceptions.NotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Database<Serializable> {
    private boolean isListLoaded = false;

    protected List<Serializable> list = new ArrayList<>();

    public abstract void add(Serializable obj);
    public abstract void delete(Serializable obj) throws NotFoundException;

    private final String databaseFile;

    protected Database(String databaseFile) {
        this.databaseFile = databaseFile;
    }
    public List<Serializable> getAll() {
        return list;
    }

    /**
     * Reads database from the DATABASE_FILE.
     */
    protected List<Serializable> read() throws IOException, ClassNotFoundException {
        List<Serializable> output = new ArrayList<>();
        File f = new File(getDatabaseFile());
        if (f.exists() && !f.isDirectory()) {
            try (FileInputStream fis = new FileInputStream(f); ObjectInputStream ois = new ObjectInputStream(fis)) {
                output = (List<Serializable>) ois.readObject();
            }
        }

        isListLoaded = true;
        return output;
    }

    /**
     * Writes the database to the DATABASE_FILE.
     */
    public void write() throws IOException {
        if (!isListLoaded) {
            return;
        }

        try (FileOutputStream fos = new FileOutputStream(getDatabaseFile()); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(list);
        }
    }

    /**
     * Generates new item ID.
     */
    protected abstract int generateId();

    protected int getItemPositionWithinList(Serializable obj) {
        int i = 0;
        for (Serializable entry : list) {
            if (entry == obj) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public abstract Serializable getById(int id);

    public String getDatabaseFile() { return databaseFile; }
}
