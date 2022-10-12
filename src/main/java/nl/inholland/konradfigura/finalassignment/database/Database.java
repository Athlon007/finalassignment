package nl.inholland.konradfigura.finalassignment.database;

import nl.inholland.konradfigura.finalassignment.model.exceptions.NotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Database<Serializable> {
    protected List<Serializable> list = new ArrayList<>();

    public abstract void add(Serializable obj);
    public abstract void delete(Serializable obj) throws NotFoundException;

    private final String databaseFile;

    protected Database(String databaseFile) {
        this.databaseFile = databaseFile;
        this.list = read();
    }
    public List<Serializable> getAll() {
        return list;
    }

    /**
     * Reads database from the DATABASE_FILE.
     */
    protected List<Serializable> read() {
        List<Serializable> output = new ArrayList<>();
        final File f = new File(getDatabaseFile());
        if (f.exists() && !f.isDirectory()) {
            try (FileInputStream fis = new FileInputStream(f); ObjectInputStream ois = new ObjectInputStream(fis)) {
                output = (List<Serializable>) ois.readObject();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return output;
    }

    /**
     * Writes the database to the DATABASE_FILE.
     */
    public void write() throws IOException {
        try (FileOutputStream fos = new FileOutputStream(getDatabaseFile()); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(list);
        }
    }

    /**
     * Generates new item ID.
     */
    protected abstract int generateId();

    public abstract Serializable getById(int id);

    public String getDatabaseFile() { return databaseFile; }
}
