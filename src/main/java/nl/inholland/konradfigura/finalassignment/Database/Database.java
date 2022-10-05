package nl.inholland.konradfigura.finalassignment.Database;

import nl.inholland.konradfigura.finalassignment.Model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Database<Serializable> {
    private boolean hasUsersListBeenLoaded = false;

    protected List<Serializable> list;

    public abstract void add(Serializable obj);
    public abstract void delete(Serializable obj);
    public List<Serializable> getAll() {
        return list;
    }

    /**
     * Reads database from the DATABASE_FILE.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    protected List<Serializable> read() throws IOException, ClassNotFoundException {
        List<Serializable> output = null;
        File f = new File(getDatabaseFile());
        if (f.exists() && !f.isDirectory()) {
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            output = (List<Serializable>)ois.readObject();
        }
        else {
            output = new ArrayList<>();
        }

        hasUsersListBeenLoaded = true;
        return output;
    }

    /**
     * Writes the database to the DATABASE_FILE.
     * @throws IOException
     */
    public void write() throws IOException {
        if (!hasUsersListBeenLoaded) {
            return;
        }

        FileOutputStream fos = new FileOutputStream(getDatabaseFile());
        ObjectOutputStream obj = new ObjectOutputStream(fos);
        obj.writeObject(list);
        obj.close();
        fos.close();
    }

    /**
     * This database's file name. MUST be overwritten.
     * @return
     */
    public abstract String getDatabaseFile();

    /**
     * Generates new item ID.
     * @return
     */
    protected abstract int generateId();

    protected int getItemPositonWithinList(Serializable obj) {
        int i = 0;
        for (Serializable entry : list) {
            if (entry == obj) {
                return i;
            }
            i++;
        }
        return -1;
    }
}
