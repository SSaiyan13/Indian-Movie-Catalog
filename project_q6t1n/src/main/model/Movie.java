package model;

import java.util.List;
import org.json.JSONObject;
import persistence.Writable;


//REPRESENTS a movie that has its name, genre, year , rating, language, actors, review box, and movie Id
public class Movie implements Writable {
    private String movieName;
    private String genre;
    private int year;
    private int rating;
    private String language;
    private List<String> actors;
    private String reviewBox;
    private String movieId;



    //EFFECTS: MAKES A MOVIE with its name, director, genre, main actor, year and rating
    public Movie(String movieName, String genre,
                 int year, int rating, String language, List<String> actors, String reviewBox, String movieId) {
        this.movieName = movieName;
        this.genre = genre;
        this.year = year;
        this.rating = rating;
        this.language = language;
        this.actors = actors;
        this.reviewBox = reviewBox;
        this.movieId = movieId;

        new Event("Movie created: " + movieName);
    }

    //Effects: gets the movies name
    public String getMovieName() {
        return this.movieName;
    }

    //Effects: gets the genre
    public String getGenre() {
        return this.genre;
    }

    //Effects: gets the year
    public int getYear() {
        return this.year;
    }

    //Effects: gets the rating
    public int getRating() {
        return this.rating;
    }

    //Effects: gets the language from the constructor
    public String getLanguage() {
        return this.language;
    }

    //Effects: gets actors in the film
    public List<String> getActors() {
        return this.actors;
    }

    //EFfects: gets the empty review part of it
    public String getReviewBox() {
        return this.reviewBox;
    }

    //Effects: gets the movieId from the construct
    public String getMovieId() {
        return this.movieId;
    }

    //Effects: sets the reviews inside the movie constructor
    public void setReviewBox(String reviewBox) {
        this.reviewBox = reviewBox;
    }

    public void logReviewAddedEvent() {
        new Event("Review added to movie: " + movieName);
    }

    public void logMovieIdAddedEvent() {
        new Event("Movie ID has been added: " + movieName);
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("movieName", movieName);
        json.put("genre", genre);
        json.put("year", year);
        json.put("rating", rating);
        json.put("language", language);
        json.put("actors", actors);
        json.put("reviewBox", reviewBox);
        json.put("movieId", movieId);
        return json;
    }
}
