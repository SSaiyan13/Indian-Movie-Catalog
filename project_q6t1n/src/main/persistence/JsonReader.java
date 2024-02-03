package persistence;

import model.Movie;

import model.MoviesCatalog;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

////REFERENCED FROM THE JSONSERIALAZATIONDEMO CODE
import org.json.*;

//Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads movie catalog from file and returns it;
    // throws IOException if an error occurs reading data from file
    public MoviesCatalog read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMoviesCatalog(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses movie catalog from JSON object and returns it
    private MoviesCatalog parseMoviesCatalog(JSONObject jsonObject) {
        MoviesCatalog catalog = new MoviesCatalog();
        JSONArray moviesArray = jsonObject.getJSONArray("movies");

        for (Object json : moviesArray) {
            JSONObject movieJson = (JSONObject) json;
            Movie movie = parseMovie(movieJson);
            catalog.addMovie(movie);
        }
        return catalog;
    }

    //EFFECTS: parses movie from JSON object and returns it
    private Movie parseMovie(JSONObject jsonObject) {
        String movieName = jsonObject.getString("movieName");
        String genre = jsonObject.getString("genre");
        int year = jsonObject.getInt("year");
        int rating = jsonObject.getInt("rating");
        String language = jsonObject.getString("language");

        List<String> actors = new ArrayList<>();
        if (jsonObject.has("actors")) {
            JSONArray actorsJsonArray = jsonObject.getJSONArray("actors");
            for (int i = 0; i < actorsJsonArray.length(); i++) {
                actors.add(actorsJsonArray.getString(i));
            }
        }

        String reviewBox = jsonObject.getString("reviewBox");
        String movieId =  jsonObject.getString("movieId");

        // You may need to parse other properties of the Movie class here

        return new Movie(movieName,genre, year, rating,language,actors, reviewBox,movieId);
    }


}
