package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class MovieCatalogTest {
    private MoviesCatalog testMovieCatalog;
    Movie barbie = new Movie("Barbie","Romance",2021,3,
            "Telugu", List.of("Ryan Gosling", "Leonardo Dicapro", "Bill Gates"),
            "", "M545");
    Movie thuppaki =  new Movie("Thuppaki", "Action", 2012,5,
            "Tamil", List.of("Thalpathy Vijay", "Vidyut Jammwal", "Kajal Aggarwal"),
            "", "M231");
    Movie jimmy = new Movie("jimmy", "Romance",2222,5,"Hindi",
            List.of("Ryan Gosling", "Leonardo Dicapro", "Bill Gates"),"", "FFF3");
    Movie real = new Movie("gg","Action",1965,5, "Telugu",
            List.of("Ryan Gosling", "Leonardo Dicapro", "Bill Gates"),"", "FFFF");

    //Movie chicchore = new Movie("Chicchore","Vineeth", "Romance")


    @BeforeEach
    void runBefore(){
        testMovieCatalog = new MoviesCatalog();
    }

    @Test
    void testAddMovieCatlog(){
        testMovieCatalog.addMovie(barbie);

    }

    @Test
    void testAddReview(){
        testMovieCatalog.addMovie(barbie);
        testMovieCatalog.addReview( barbie.getMovieId(), "11/10 Best film of the year");

        assertEquals("11/10 Best film of the year", barbie.getReviewBox());

    }

    @Test
    void testAddReviewToNonExistentMovie(){
        testMovieCatalog.addMovie(barbie);
        String addedReviewMovie = testMovieCatalog.getReviewAdded(thuppaki.getMovieId());

        assertNull(addedReviewMovie);

    }

    @Test
    void testAddMulitpleReview(){
        testMovieCatalog.addMovie(barbie);
        testMovieCatalog.addReview(barbie.getMovieId(), "11/10 Best film of the year!");
        String addedReviewMovie = testMovieCatalog.getReviewAdded(barbie.getMovieId());
        assertEquals("11/10 Best film of the year!", addedReviewMovie);

        testMovieCatalog.addMovie(thuppaki);
        testMovieCatalog.addReview(thuppaki.getMovieId(),"It was lit");
        assertEquals("It was lit", testMovieCatalog.getReviewAdded(thuppaki.getMovieId()));
    }

    @Test
    void testAddMovie(){
        testMovieCatalog.addMovie(barbie);

        assertEquals(testMovieCatalog.getMovies().size(),1);
        assertEquals(testMovieCatalog.getMovies().get(0),barbie);

    }

    @Test
    void testAddMultipleMovie(){
        testMovieCatalog.addMovie(barbie);
        testMovieCatalog.addMovie(thuppaki);

        assertEquals(testMovieCatalog.getMovies().size(),2);
        assertEquals(testMovieCatalog.getMovies().get(0),barbie);
        assertEquals(testMovieCatalog.getMovies().get(1),thuppaki);

    }

    @Test
    void testRemoveMovie(){
        testMovieCatalog.addMovie(barbie);

        assertEquals(testMovieCatalog.getMovies().size(),1);
        assertEquals(testMovieCatalog.getMovies().get(0),barbie);

        testMovieCatalog.removeMovie(barbie.getMovieId());
        assertEquals(testMovieCatalog.getMovies().size(),0);

    }

    @Test
    void testRemoveMultipleMovie(){
        testMovieCatalog.addMovie(barbie);
        testMovieCatalog.addMovie(thuppaki);

        assertEquals(testMovieCatalog.getMovies().size(),2);
        assertEquals(testMovieCatalog.getMovies().get(0),barbie);

        testMovieCatalog.removeMovie(barbie.getMovieId());
        assertEquals(testMovieCatalog.getMovies().size(),1);

        assertEquals(testMovieCatalog.getMovies().get(0),thuppaki);
        testMovieCatalog.removeMovie(thuppaki.getMovieId());
        assertEquals(testMovieCatalog.getMovies().size(),0);

    }



    @Test
    void testFilterByTitle(){
        testMovieCatalog.addMovie(barbie);
        testMovieCatalog.addMovie(thuppaki);
        List< Movie> title =  testMovieCatalog.filterByTitle("Barbie");;
        assertEquals(1, title.size());
        assertTrue(title.get(0).getMovieName().startsWith("Barbie"));

    }

    @Test
    void testFilterByLanguageAndGenre() {
        testMovieCatalog.addMovie(barbie);
        testMovieCatalog.addMovie(thuppaki);
        testMovieCatalog.addMovie(jimmy);
        testMovieCatalog.addMovie(real);

        String language = "Telugu";
        String genre = "Romance";
        List<Movie> filterMovies = testMovieCatalog.filterByLanguageAndGenre(language, genre);

        assertEquals(1, filterMovies.size());
        Movie filteredMovie = filterMovies.get(0);

        assertEquals(language, filteredMovie.getLanguage());
        assertEquals(genre, filteredMovie.getGenre());
    }






}
