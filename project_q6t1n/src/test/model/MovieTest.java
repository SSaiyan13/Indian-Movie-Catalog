package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {
    private Movie testMovie;
    ArrayList<String> languages;
    @BeforeEach
    void runBefore() {
        testMovie = new Movie("Barbie","Romance",2021,3,
                "tamil", List.of("Ryan Gosling", "Leonardo Dicapro", "Bill Gates"),
                "", "M545");
        languages = new ArrayList<>();
        languages.add("Tamil");
        languages.add("Telugu");
        languages.add("Malayalnam");
        languages.add("Kannada");
        languages.add("Hindi");

    }

    @Test
    //EFFECTS: Test it has a movie with one of these languages Tamil, Telugu, Malayalnam, Kannada, Hindi
    void languageMovies(){
        boolean isTrue = false;
        Movie thuppaki = new Movie("Thuppaki", "Action", 2012,5,
                "Tamil", List.of("Thalpathy Vijay", "Vidyut Jammwal", "Kajal Aggarwal"),
                "", "M231");
        for (String language:  this.languages) {
            if (language.equals("Tamil")){
                isTrue = true;
            }
        }
        assertTrue(isTrue);
    }




}