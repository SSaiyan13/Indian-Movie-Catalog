package ui;

import model.Event;
import model.EventLog;
import model.Movie;
import model.MoviesCatalog;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import java.io.FileNotFoundException;

import java.io.IOException;

//REPRESENTS THE TEXT BASED CONSOLE WHERE YOU CAN TRY TO ADD,
// REMOVE, VIEW, FILTERING, SAVE, AND LOAD THE MOVIE CATALOG
// SG Movie Catalog application
public class SGMoviesCatalog {
    private MoviesCatalog moviesList;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private static final String JSON_STORE = "./data/moviesCatalog.json";


    // EFFECTS: constructs the movie catalog and runs the application
    public SGMoviesCatalog() throws FileNotFoundException {
        moviesList = new MoviesCatalog();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runSGMovie();
    }

    //MODIFIES:
    //EFFECTS: processess user input
    private void runSGMovie() {
        boolean keepGoing = true;
        String command = null;

        displayMenu();

        Scanner scanner = new Scanner(System.in);

        while (keepGoing) {
            command = scanner.nextLine();
            processCommand(command);
        }

    }

    //EFFECTS: the display menu of options to the user
    private void displayMenu() {
        System.out.println("\nWelcome to the SGMoviesCatalog what do you want to do?");
        System.out.println("\tAdd a Movie to the list <- type: add ");
        System.out.println("\tRemove a movie to the list <- type: remove");
        System.out.println("\tDo want to add a review <- type: review");
        System.out.println("\tDo want to search for a movie? <- type: search ");
        System.out.println("\tDo you want to filter your movie list? <- type: filter  ");
        System.out.println("\tdo want to view a list <- type: view ");
        System.out.println("\tdo want to save your list <- type: save ");
        System.out.println("\tdo want to load your list <- type: load ");

    }

    //MODIFIES: this
    //EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("add")) {
            addingMovies();
        } else if (command.equals("remove")) {
            removingMovies();
        } else if (command.equals("review")) {
            doReviews();
        } else if (command.equals(("search"))) {
            doSearch();
        } else if (command.equals("view")) {
            doViewAll();
        } else if (command.equals("filter")) {
            filteredMovies();
        } else if (command.equals("save")) {
            saveMoviesCatalog();
        } else if (command.equals("load")) {
            loadMovieCatalog();
        } else {
            System.out.println("Invalid. Try Again");
        }
    }

    //Effects: helps makes the prompts by combining printing and scanner.next line
    public String prompt(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        return scanner.nextLine();
    }

    //REQUIRES: need have 1 movie in the list
    //EFFECTS: views the movie catalog you have
    public void doViewAll() {
        for (Movie movie: moviesList.getMovies()) {
            System.out.println(movie.getMovieName());
        }
    }


    //EFFECTS: Adds the movies into the catalog
    private void addingMovies() {
        Scanner scanner = new Scanner(System.in);
        String movieName = prompt("What is the name of the movie:  ");
        String genre = prompt("What is the genre?:  ");

        System.out.println("What year was it made:  ");
        int year = scanner.nextInt();

        System.out.println("What is the rating our of 5 (no decimals):  ");
        int rating = scanner.nextInt();
        scanner.nextLine();

        String language = prompt("What language is it in (choose from -> Tamil,Telugu,Hindi,Malayalam,Kannada):  ");

        String actorsList = prompt("Enter the actors (separated by commas): ");

        String[] actorNames = actorsList.split(",");
        List<String> actors = new ArrayList<>(Arrays.asList(actorNames));

        String movieId = prompt("Enter the movieId (combination of letters and numbers): ");

        String review = "";
        System.out.println("Do you want to add a review? (say yes or no): ");
        if (scanner.next().equals("yes")) {
            System.out.println("Enter your review: ");
            review = scanner.nextLine();
        }
        Movie newMovie = new Movie(movieName, genre, year, rating, language, actors, review, movieId);
        moviesList.addMovie(newMovie);
        System.out.println("Movie added to the catalog.");
    }

    //REQUIRES: At least 1 movie in the list
    //EFFECTS: removes a movie or movies from your catalog
    private void removingMovies() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the movieId of the movie you want to remove: ");
        String movieId = scanner.nextLine();
        moviesList.removeMovie(movieId);

        System.out.println("This movie removed from the catalog.");
    }

    //EFFECTS: allows you to write reviews to the movie you want
    private void doReviews() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the movieId of the movie you want to review: ");
        String movieId = scanner.nextLine();

        System.out.println("Enter your review: ");
        String review = scanner.nextLine();

        moviesList.addReview(movieId, review);
        System.out.println("Review added for the movie.");
    }

    //REQUIRES: need to have at least 1 movie in your list
    //EFFECTS: Searches the movie in your catalog
    private void doSearch() {
        Scanner scanner = new Scanner(System.in);
        String title = scanner.nextLine();

        List<Movie> searchedMovies = moviesList.filterByTitle(title);

        if (searchedMovies.isEmpty()) {
            System.out.println(("No movies found by the given search"));

        } else {
            System.out.println("Movies with the title \"" + title + "\":");
            for (Movie movie : searchedMovies) {
                System.out.println(movie.getMovieName());
            }

        }
    }

    //REQUIRES: needs to filtered by language,genre and title first
    //EFFECTS: shows  the filtered list movies by languages or genre
    public void filteredMovies() {
        String language = prompt("Enter the language to filter by: ");
        String genre = prompt("Enter the genre to filter by: ");

        List<Movie> filteredMovies = moviesList.filterByLanguageAndGenre(language, genre);

        if (filteredMovies.isEmpty()) {
            System.out.println("No movies found matching the criteria.");
        } else {
            System.out.println("Filtered Movies:");
            for (Movie movie : filteredMovies) {
                System.out.println(movie.getMovieName());
            }
        }


    }

    // EFFECTS: saves the movies catalog to the JSON file
    private void saveMoviesCatalog() {
        try {
            jsonWriter.open();
            jsonWriter.writeMoviesCatalog(moviesList);
            jsonWriter.close();
            System.out.println("Saved the movies catalog to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to the JSON file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads movie catalog from file
    private void loadMovieCatalog() {
        try {
            moviesList = jsonReader.read();
            System.out.println("Loaded movie catalog from " + JSON_STORE);
            EventLog.getInstance().logEvent(new Event("Movies were loaded from: "));
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}




