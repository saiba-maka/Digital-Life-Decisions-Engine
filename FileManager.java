package util;

import model.Decision;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FileManager {

    private static final String FILE_NAME = "decisions.txt";

    // Save a collection of decisions to file
    public static void save(Collection<Decision> decisions) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(new ArrayList<>(decisions));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load decisions from file
    public static List<Decision> load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Decision>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>(); // Return empty list if file not found or error
        }
    }
}
