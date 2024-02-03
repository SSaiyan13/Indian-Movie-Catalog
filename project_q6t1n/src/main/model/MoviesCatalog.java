package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.Writable;

// Represents the main catalog allowing the movies being added, remove it by its movie ID
// , filtering the movies by the language, genre
// and title
public class MoviesCatalog implements Writable {
    private List<Movie> movies;
    private Movie reviewAdded;
    private String titlePrefix;
    private List<String> genres;
    private String languageSorted;
    private List<String> languages;

    //REQUIRES: At least 1 movie in the catalog
    //EFFECTS: Shows the list of movies inside the catalog, has a title to prove it
    public MoviesCatalog() {
        this.movies = new ArrayList<>();
        this.languages = new ArrayList<>();
        languages.add("Tamil");
        languages.add("Telugu");
        languages.add("Malayalam");
        languages.add("Kannada");
        languages.add("Hindi");
    }


    //EFFECTS add a movie into the Catalog
    public void addMovie(Movie add) {
        movies.add(add);
        EventLog.getInstance().logEvent(new Event("Movie added to catalog: " + add.getMovieName()));
    }

    //EFFECTS remove a movie from the catalog
    public void removeMovie(String movieId) {
        movies.removeIf(movie -> movie.getMovieId().equals(movieId));
        EventLog.getInstance().logEvent(new Event("Movie removed from catalog: " + movieId));
    }

    //REQUIRES: at least 1 movie is in the catlog
    //EFFECTS: adds reviews to a movie by typing the movie's id
    public void addReview(String movieId, String review) {
        for (Movie movie : movies) {
            if (movie.getMovieId().equals(movieId)) {
                movie.setReviewBox(review);
                movie.logReviewAddedEvent();
            }
        }
    }


    //MODIFIES: this
    //Effects: filter the movie by its title by the first character in the title name
    public List<Movie> filterByTitle(String titlePrefix) {
        this.titlePrefix = titlePrefix;
        this.movies = movies;
        int length = this.movies.size();
        ArrayList<Movie> out = new ArrayList<Movie>();
        for (int i = 0; i < length; i++) {
            if (movies.get(i).getMovieName().startsWith(titlePrefix)) {
                out.add(movies.get(i));
            }
        }
        return out;
    }

    //REQUIRES: Movies must be one of these languages (Tamil, Telugu, Hindi, Malayalam)
    //EFFECTS: filters the movies by the one of the 5 languages and genre
    public List<Movie> filterByLanguageAndGenre(String language, String genre) {
        // Create a new list to store filtered movies
        List<Movie> filteredMovies = new ArrayList<>();

        // Iterate through the movies in the catalog
        for (Movie movie : movies) {
            // Check if the movie matches the specified language and genre
            if (movie.getLanguage().equalsIgnoreCase(language) && movie.getGenre().equalsIgnoreCase(genre)) {
                // If it matches, add it to the filtered list
                filteredMovies.add(movie);
            }
        }

        return filteredMovies;
    }


    //EFFECTS: gets the review added to the movie in the catalog
    public String getReviewAdded(String movieId) {
        for (Movie movie : movies) {
            if (movie.getMovieId().equals(movieId)) {
                return movie.getReviewBox();
            }
        }
        return null;
    }

    //EFFECTS: helps the GUI with removing a movie by its movieID
    public boolean removeMovieById(String movieId) {
        for (Movie movie : movies) {
            if (movie.getMovieId().equals(movieId)) {
                movies.remove(movie);
                EventLog.getInstance().logEvent(new Event("Movie removed from catalog: " + movieId));
                return true;
            }
        }
        return false;
    }


    //EFFEECTS: gets the list of movies
    public List<Movie> getMovies() {
        return this.movies;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("movies", moviesToJson());

        return json;
    }

    //EFFECTS; returns movies into the movies catalog as a JsonArray
    private JSONArray moviesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Movie movie : movies) {
            jsonArray.put(movie.toJson());
        }
        return jsonArray;
    }


}
