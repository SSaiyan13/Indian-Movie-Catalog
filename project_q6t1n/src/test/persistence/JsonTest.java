package persistence;

import model.Movie;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//NOTE:
// Need to call CheckMovie methods to see all the fields are correct
// Beyond that point you always check the movie by the CheckMovieById

//REFERENCED FROM THE JSONSERIALAZATIONDEMO CODE

public class JsonTest {

    protected void checkMovieById(Movie movie, String movieId){
        assertEquals(movie.getMovieId(), movieId);
    }

    protected void checkMovie(Movie movie, String movieName, String genre, int year, int rating, String language,
                              List<String> actors, String reviewBox, String movieId){
        assertEquals(movie.getMovieName(),movieName);
        assertEquals(movie.getGenre(),genre);
        assertEquals(movie.getYear(),year);
        assertEquals(movie.getRating(),rating);
        assertEquals(movie.getLanguage(),language);
        assertEquals(movie.getActors(),actors);
        assertEquals(movie.getReviewBox(),reviewBox);
        assertEquals(movie.getMovieId(),movieId);

    }
}
