package persistence;

import model.Movie;
import model.MoviesCatalog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

//REFERENCED FROM THE JSONSERIALAZATIONDEMO CODE

public class JsonWriterTest extends JsonTest {
    private MoviesCatalog catalog;

    @BeforeEach
    void runBefore() {
        catalog = new MoviesCatalog();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            MoviesCatalog catalog = new MoviesCatalog();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            writer.writeMoviesCatalog(catalog);
            writer.close();
            fail("IOException was expected");
        } catch (IOException e) {
            // The exception is expected since the file path is invalid
        }
    }


    @Test
    void testWriterEmptyCatalog() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCatalog.json");
            writer.open();
            writer.writeMoviesCatalog(catalog);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCatalog.json");
            catalog = reader.read();
            assertEquals(0, catalog.getMovies().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyMovieCatalog() {
        try {
            MoviesCatalog catalog = new MoviesCatalog();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyMovieCatalog.json");
            writer.open();
            writer.writeMoviesCatalog(catalog);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyMovieCatalog.json");
            catalog = reader.read();
            assertEquals(0, catalog.getMovies().size());

            // Assuming you want to ensure languages were correctly initialized within the first movie in the catalog
            if (!catalog.getMovies().isEmpty()) {
                Movie firstMovie = catalog.getMovies().get(0);
                assertTrue(firstMovie.getLanguage().contains("Tamil"));
            }

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }



    @Test
    void testWriterGeneralMovieCatalog() {
        // Create some sample movies to add to the catalog
        List<String> actors1 = new ArrayList<>();
        actors1.add("MARGOT ROBBIE");
        actors1.add("RYAN GOSLING");
        Movie barbie = new Movie("Barbie", "Comedy", 2021, 5, "Tamil",
                actors1, "", "F45W");

        catalog.addMovie(barbie);

        try {
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralMovieCatalog.json");
            writer.open();
            writer.writeMoviesCatalog(catalog);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralMovieCatalog.json");
            catalog = reader.read();
            assertEquals(1, catalog.getMovies().size());

            // Check the first movie in the catalog using checkMovieById
            Movie movie = catalog.getMovies().get(0);
            checkMovie(movie, "Barbie","Comedy",2021,5, "Tamil", actors1,
                    "", "F45W");
            checkMovieById(movie, "F45W");
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


    @Test
    void testWriterGeneralCatalogWithMultipleMovies(){
        List<String> actors1 = new ArrayList<>();
        actors1.add("MARGOT ROBBIE");
        actors1.add("RYAN GOSLING");
        Movie barbie = new Movie("Barbie", "Comedy", 2021, 5, "Tamil",
                actors1, "", "F45W");

        catalog.addMovie(barbie);

        List<String> actors2 = new ArrayList<>();
        actors2.add("KEANU REEVES");
        actors2.add("CARRIE-ANNE MOSS");
        Movie matrix = new Movie("The Matrix", "Sci-Fi", 1999, 8, "Hindi",
                actors2, "", "K32X");
        catalog.addMovie(matrix);

        try {
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCatalogWithMultipleMovies.json");
            writer.open();
            writer.writeMoviesCatalog(catalog);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCatalogWithMultipleMovies.json");
            catalog = reader.read();
            assertEquals(2, catalog.getMovies().size());

            // Check the first movie in the catalog
            Movie movie1 = catalog.getMovies().get(0);
            checkMovie(movie1, "Barbie", "Comedy", 2021, 5, "Tamil", actors1,
                    "", "F45W");

            // Check the second movie in the catalog
            Movie movie2 = catalog.getMovies().get(1);
            checkMovie(movie2, "The Matrix", "Sci-Fi", 1999, 8, "English", actors2,
                    "", "K32X");

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }

    }

    @Test
    void testWriterGeneralCatalogWithNullActors() {
        Movie barbie = new Movie("Barbie", "Comedy", 2021, 5, "Tamil",
                null, "", "F45W");

        catalog.addMovie(barbie);

        Movie matrix = new Movie("The Matrix", "Sci-Fi", 1999, 8, "Hindi",
                null, "", "K32X");
        catalog.addMovie(matrix);

        try {
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCatalogWithMultipleMovies.json");
            writer.open();
            writer.writeMoviesCatalog(catalog);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCatalogWithMultipleMovies.json");
            catalog = reader.read();
            assertEquals(2, catalog.getMovies().size());

            // Check the first movie in the catalog
            Movie movie1 = catalog.getMovies().get(0);
            checkMovie(movie1, "Barbie", "Comedy", 2021, 5, "Tamil", new ArrayList<>(),
                    "", "F45W");

            // Check the second movie in the catalog
            Movie movie2 = catalog.getMovies().get(1);
            checkMovie(movie2, "The Matrix", "Sci-Fi", 1999, 8, "Hindi", new ArrayList<>(),
                    "", "K32X");

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}

