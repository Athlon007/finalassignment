package nl.inholland.konradfigura.finalassignment.dataaccess;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Database<T> {
    /**
     * Reads database from the DATABASE_FILE.
     */
    public List<T> read(String file) {
        List<T> output = new ArrayList<>();
        final File f = new File(file);
        if (f.exists() && !f.isDirectory()) {
            try (FileInputStream fis = new FileInputStream(f); ObjectInputStream ois = new ObjectInputStream(fis)) {
                output = (List<T>) ois.readObject();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return output;
    }

    /**
     * Writes the database to the DATABASE_FILE.
     */
    public void write(String file, List<T> list) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(list);
        }
    }
}
