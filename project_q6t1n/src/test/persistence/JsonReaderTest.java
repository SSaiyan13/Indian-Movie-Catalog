package persistence;

import model.Movie;
import model.MoviesCatalog;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//REFERENCED FROM THE JSONSERIALAZATIONDEMO CODE

public class JsonReaderTest extends JsonTest{
    private JsonReader jsonReader;
    private MoviesCatalog catalog;

    @BeforeEach
    void runBefore() {
        jsonReader = new JsonReader("./data/testWriterEmptyMovieCatalog.json");
        catalog = new MoviesCatalog();
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            MoviesCatalog m = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyMovieCatalog() {
        try {
            catalog = jsonReader.read();
            assertEquals(0, catalog.getMovies().size());

            for (Movie movie : catalog.getMovies()) {
                assertEquals("Tamil", movie.getLanguage());
            }
        } catch (IOException e) {
            fail("Couldn't read from the file");
        }
    }



    @Test
    void testReaderGeneralMovieCatalog() {
        JsonReader reader = new JsonReader("./data/OneActorReader.json");
        try {
            MoviesCatalog catalog = reader.read();

            List<Movie> movies = catalog.getMovies();
            assertEquals(1, movies.size());

            // Customize these details based on your movie data
            checkMovie(movies.get(0), "Movie1", "Genre1", 2022, 5, "Language1",
                    Arrays.asList("Jimmy"), "Review1", "ID1");
            checkMovieById(movies.get(0), "ID1");

        } catch (IOException e) {
            e.printStackTrace();
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralMovieCatalogWithActor() {
        JsonReader reader = new JsonReader("./data/OneActorReader.json");
        try {
            MoviesCatalog catalog = reader.read();

            List<Movie> movies = catalog.getMovies();
            assertEquals(1, movies.size());

            // Customize these details based on your movie data
            checkMovie(movies.get(0), "Movie1", "Genre1", 2022, 5, "Language1",
                    Arrays.asList("Jimmy"), "Review1", "ID1");
            checkMovieById(movies.get(0), "ID1");

        } catch (IOException e) {
            e.printStackTrace();
            fail("Couldn't read from file");
        }
    }


    @Test
    void testReaderGeneralMovieNoActors() {
        JsonReader reader = new JsonReader("./data/READERTest.json");
        try {
            MoviesCatalog catalog = reader.read();

            List<Movie> movies = catalog.getMovies();
            assertEquals(1, movies.size());

            // Customize these details based on your movie data
            checkMovie(movies.get(0), "The Matrix", "Sci-Fi", 1999, 8, "Hindi",
                    new ArrayList<>(), "", "K32X");
            checkMovieById(movies.get(0), "K32X");

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }









}
