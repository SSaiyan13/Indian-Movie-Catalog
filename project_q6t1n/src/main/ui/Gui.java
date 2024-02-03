package ui;

import model.EventLog;
import model.Event;
import model.MoviesCatalog;
import model.Movie;
import org.json.JSONException;
import persistence.JsonWriter;
import persistence.JsonReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Arrays;

// REPRESENT THE VISUALER OF MY CATALOG IT WILL HAVE 6 BUTTONS VIEW, ADD, REMOVE, SAVE, AND LOAD MOVIES
//
public class Gui extends JFrame implements ActionListener {

    private static final String MOVIELIST_FILE = "./data/movielist.txt";
    private MoviesCatalog movieCatalog;


    private JPanel mainMenu;
    private JButton viewMoviesButton;
    private JButton addMovieButton;
    private JButton removeMovieButton;
    private JButton saveMoviesButton;
    private JButton loadMoviesButton;
    private JButton exitButton;

    private JPanel movieListPanel;
    private JTextArea movies;

    private JPanel addMoviePanel;
    private JTextField titleTextField;
    private JTextField directorTextField;
    private JTextField genreTextField;
    private JTextField yearTextField;
    private JTextField ratingTextField;
    private JTextField languageTextField;
    private JTextField actorsTextField;
    private JTextField reviewTextField;
    private JTextField movieIdTextField;

    private static final String EVENT_LOG_FILE = "./data/eventlog.txt";


    public Gui() {
        super("SG Movie Catalog App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(550, 650));
        initializeMenu();
        makeMovieListPanel();


        movieCatalog = new MoviesCatalog();

        setUpFields();
        makeAddMoviePanel();
        add(addMoviePanel);

        JLabel welcomeLabel = new JLabel("SG Movie Catalog!");
        addLabel(welcomeLabel);

        initializeMenuButtons();
        addButtons(viewMoviesButton, addMovieButton, removeMovieButton, saveMoviesButton, loadMoviesButton, exitButton);

        addActionToButton();

        image();
        mainMenu.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        mainMenu.setVisible(true);
    }

    //Effects: helps adding the text fields in add movie panels
    public void setUpFields() {
        titleTextField = new JTextField();
        directorTextField = new JTextField();
        genreTextField = new JTextField();
        yearTextField = new JTextField();
        ratingTextField = new JTextField();
        languageTextField = new JTextField();
        actorsTextField = new JTextField();
        reviewTextField = new JTextField();
        movieIdTextField = new JTextField();
    }

    //Effects adds the image in the main menu
    public void image() {
        ImageIcon icon = new ImageIcon("data/clapper-board-icon-movie-symbol-flat-vector-6557016.jpg");
        icon = new ImageIcon(icon.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setAlignmentY(JComponent.CENTER_ALIGNMENT);
        iconLabel.setVerticalAlignment(SwingConstants.CENTER);
        mainMenu.add(iconLabel);
    }

    //EFFECT: Initalize to the menu page and does the alignment
    public void initializeMenu() {
        mainMenu = new JPanel();
        mainMenu.setLayout(new BoxLayout(mainMenu,BoxLayout.Y_AXIS));
        mainMenu.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        //mainMenu.setHorizontalAlignment(SwingConstants.CENTER);
        mainMenu.setAlignmentY(JComponent.CENTER_ALIGNMENT);
        //mainMenu.setVerticalAlignment(SwingConstants.CENTER);
        mainMenu.setBackground(Color.lightGray);
        add(mainMenu);
        movies = new JTextArea();
        movies.setText("No Movies available");
    }

    //EFFECTS: inializes all the menu buttons
    public void initializeMenuButtons() {
        viewMoviesButton = new JButton("View Movies");
        addMovieButton = new JButton("Add Movie");
        removeMovieButton = new JButton("Remove Movie");
        saveMoviesButton = new JButton("Save Movies");
        loadMoviesButton = new JButton("Load Movies");
        exitButton = new JButton("Exit");
    }


    //EFFECTS: makes the buttons image and does the alignment
    public void addButton(JButton button, JPanel panel) {
        button.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setAlignmentY(JComponent.CENTER_ALIGNMENT);
        button.setVerticalAlignment(SwingConstants.CENTER);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(Color.white);
        panel.add(button);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    //EFFECTS: Adding buttons to the main menu
    public void addButtons(JButton viewMoviesButton, JButton addMovieButton,JButton removeMovieButton,
                           JButton saveMoviesButton, JButton loadMoviesButton, JButton exitButton) {
        addButton(viewMoviesButton, mainMenu);
        addButton(addMovieButton, mainMenu);
        addButton(removeMovieButton, mainMenu);
        addButton(saveMoviesButton, mainMenu);
        addButton(loadMoviesButton, mainMenu);
        addButton(exitButton, mainMenu);
    }

    // Effects: add
    public void addMenuButton(JButton button, JPanel panel) {
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.white);
        panel.add(button);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    //EFFECTS: adds the labels
    public void addLabel(JLabel label) {
        label.setFont(new Font("ComicSans", Font.BOLD, 50));
        label.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setAlignmentY(JComponent.CENTER_ALIGNMENT);
        label.setVerticalAlignment(SwingConstants.CENTER);
        mainMenu.add(label);
    }

    //EFFECTS: does the action once the button is pressed
    public void addActionToButton() {
        viewMoviesButton.addActionListener(this);
        viewMoviesButton.setActionCommand("View Movies");
        addMovieButton.addActionListener(this);
        addMovieButton.setActionCommand("Add Movie");
        removeMovieButton.addActionListener(this);
        removeMovieButton.setActionCommand("Remove Movie");
        saveMoviesButton.addActionListener(this);
        saveMoviesButton.setActionCommand("Save Movies");
        exitButton.addActionListener(this);
        exitButton.setActionCommand("Exit");
        loadMoviesButton.addActionListener(this);
        loadMoviesButton.setActionCommand("Load Movies");
    }


    private void printEventLog() {
        EventLog eventLog = EventLog.getInstance();
        System.out.println("Event Log:");
        for (Event event : eventLog) {
            System.out.println(event);
        }
    }

    //EFFECTS: performs what the action commands for the designated buttons
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("View Movies")) {
            displayMovies();
        } else if (ae.getActionCommand().equals("Add Movie")) {
            addMovie();
        } else if (ae.getActionCommand().equals("Remove Movie")) {
            removeMovie();
        } else if (ae.getActionCommand().equals("Save Movies")) {
            saveMovies();
        } else if (ae.getActionCommand().equals("Load Movies")) {
            loadMovies();
        } else if (ae.getActionCommand().equals("Return to main menu")) {
            returnToMainMenu();
        } else if (ae.getActionCommand().equals("Exit")) {
            printEventLog();
            System.exit(0);
        }
    }

    //MODIFIES: this
    //EFFECTS: Creates the panel that displays your current movie list
    public void makeMovieListPanel() {
        movieListPanel = new JPanel(new GridLayout(2,1));
        JScrollPane scroll = new JScrollPane(movies, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JButton mainMenuButton = new JButton("Return to Main Menu");
        mainMenuButton.setActionCommand("Return to main menu");
        mainMenuButton.addActionListener(this);
        addMenuButton(mainMenuButton, movieListPanel);


        movies.setFont(new Font("ComicSans", Font.BOLD, 12));
        movieListPanel.add(scroll);

    }


    // MODIFIES: this
    // EFFECTS: Creates the panel that displays the option for the user to input their movies
    public void makeAddMoviePanel() {
        addMoviePanel = new JPanel(new GridLayout(10, 2));
        JButton mainMenuButton = new JButton("Return to Main Menu");
        mainMenuButton.setActionCommand("Return to main menu");
        mainMenuButton.addActionListener(this);
        addMenuButton(mainMenuButton, addMoviePanel);
        createAddMovieButton();

        String[] labels = {"Title", "Genre", "Year", "Director", "Rating (1-5) ",
                "Language (Tamil,Telugu,Hindi,Malayalam,Kannada) ", "Actors (comma-separated)", "Review", "Movie ID"};
        JTextField[] textFields = {titleTextField, genreTextField, yearTextField, directorTextField, ratingTextField,
                languageTextField, actorsTextField, reviewTextField, movieIdTextField};

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i] + ":");
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            addMoviePanel.add(label);
            addMoviePanel.add(textFields[i]);
        }

        addMoviePanel.setVisible(false);

    }



    //EFFECTS: To show a lisiting of all your movies list right now
    private void displayMovies() {
        add(movieListPanel);
        movieListPanel.setVisible(true);
        mainMenu.setVisible(false);
        addMoviePanel.setVisible(false);
        StringBuilder movieInfoBuilder = new StringBuilder();
        movieInfoBuilder.append(String.format("%-20s %-10s%n", "Title: ","Movie ID: ")).append("\n\n");

        if (movieCatalog != null && !movieCatalog.getMovies().isEmpty()) {
            for (Movie movie : movieCatalog.getMovies()) {
                movieInfoBuilder.append(String.format("%-20s %-10s%n",movie.getMovieName(),movie.getMovieId()));
            }
            movies.setText(movieInfoBuilder.toString());
        } else {
            movies.setText("No movies available");
        }
    }

    // Modifies this
    // Effects: shows where the add movie has to be in the panel
    private void addMovie() {
        add(addMoviePanel);
        addMoviePanel.setVisible(true);
        mainMenu.setVisible(false);
        movieListPanel.setVisible(false);
    }


    //EFFECTS; Makes the add movie button into the main menu
    private void createAddMovieButton() {
        JButton addMovieButton = new JButton("Add Movie");
        addMovieButton.setActionCommand("Add Movie");
        addMovieButton.addActionListener(e -> addMovieToCatalog());
        addMenuButton(addMovieButton, addMoviePanel);
    }

    //EFFECTS: Shows the movies added to catalog
    private void addMovieToCatalog() {
        if (initializeCatalog()) {
            if (validateFields()) {
                Movie newMovie = createMovieFromFields();
                movieCatalog.addMovie(newMovie);
                System.out.println("Movie added: " + newMovie);
                displayMovies();
            }
        }
    }

    //EFFECTS: initalize the movie catalog
    private boolean initializeCatalog() {
        if (movieCatalog == null) {
            return false;
        }
        return true;
    }

    //EFFECTS: checks if all the movie fields is included if not need to print
    //         Please fill in all the required fields
    private boolean validateFields() {
        if (validateTextField(titleTextField)
                && validateTextField(genreTextField)
                && validateTextField(yearTextField)
                && validateTextField(directorTextField)
                && validateTextField(ratingTextField)
                && validateTextField(languageTextField)
                && validateTextField(actorsTextField)
                && validateTextField(reviewTextField)
                && validateTextField(movieIdTextField)) {
            return true;
        } else {
            System.out.println("Please fill in all the required fields.");
            return false;
        }
    }

    //Effects: check if the text field is originally empty
    private boolean validateTextField(JTextField textField) {
        if (textField != null && !textField.getText().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    //EFFECTS: it makes the movies from all the fields
    private Movie createMovieFromFields() {
        try {
            return new Movie(
                    titleTextField.getText(),
                    genreTextField.getText(),
                    Integer.parseInt(yearTextField.getText()),
                    Integer.parseInt(ratingTextField.getText()),
                    languageTextField.getText(),
                    Arrays.asList(actorsTextField.getText().split(", ")),
                    reviewTextField.getText(),
                    movieIdTextField.getText()
            );
        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric input. Please enter valid numbers for 'Year' and 'Rating'.");
            return null;
        }
    }


    //EFFECTS: Removes the movies from DisplayMovie by typing the movie Id
    private void removeMovie() {
        String movieIdToRemove = JOptionPane.showInputDialog("Enter Movie ID to remove:");

        if (movieIdToRemove != null && !movieIdToRemove.isEmpty()) {
            if (movieCatalog != null) {
                boolean removed = movieCatalog.removeMovieById(movieIdToRemove);
                if (removed) {
                    System.out.println("Movie with ID " + movieIdToRemove + " removed.");
                    displayMovies();
                } else {
                    System.out.println("Movie with ID " + movieIdToRemove + " not found.");
                }
            } else {
                System.out.println("No movies to remove.");
            }
        } else {
            System.out.println("Invalid Movie ID.");
        }
    }



    //EFFECTS: Saves the movies from the current added listings
    private void saveMovies() {
        try {
            if (movieCatalog != null) {
                JsonWriter jsonWriter = new JsonWriter(MOVIELIST_FILE);
                jsonWriter.open();
                jsonWriter.writeMoviesCatalog(movieCatalog);
                jsonWriter.close();
                System.out.println("Movies saved to " + MOVIELIST_FILE);
                EventLog.getInstance().logEvent(new Event("Movies were saved in the Catalog"));
            } else {
                System.out.println("No movies to save.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save movies to " + MOVIELIST_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //MODIFIES: this
    //EFFECTS: Loads the movies from movelistings.tct file if it exist otherwise prints error
    private void loadMovies() {
        JsonReader jsonReader = new JsonReader(MOVIELIST_FILE);
        try {
            movieCatalog = jsonReader.read();
            System.out.println("Movies loaded from " + MOVIELIST_FILE);
            EventLog.getInstance().logEvent(new Event("Movies were loaded from: "));
        } catch (IOException e) {
            handleLoadMoviesException(e);
        } catch (JSONException e) {
            handleLoadMoviesException(e);
        }
    }

    //EFFECTS: Has loadMovies exceptions
    private void handleLoadMoviesException(Exception e) {
        if (e instanceof IOException) {
            System.out.println("Unable to load movies from " + MOVIELIST_FILE);
        } else if (e instanceof JSONException) {
            System.out.println("Invalid JSON format in " + MOVIELIST_FILE);
        } else {
            e.printStackTrace();
        }
    }

    // EFFECTS: Sets all panels' visibility to false except for the main menu
    public void returnToMainMenu() {
        mainMenu.setVisible(true);
        movieListPanel.setVisible(false);
        addMoviePanel.setVisible(false);
    }


}

