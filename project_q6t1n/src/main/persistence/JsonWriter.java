package persistence;

import model.MoviesCatalog;
import org.json.JSONObject;
import java.io.*;

//REFERENCED FROM THE JSONSERIALAZATIONDEMO CODE

// Represents a writer that writes JSON representation of workroom to file
public class JsonWriter {
    public static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes a MoviesCatalog to file
    public void writeMoviesCatalog(MoviesCatalog movieCatalog) {
        JSONObject json = movieCatalog.toJson();
        saveToFile(json.toString(TAB));
    }


    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }


    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}
