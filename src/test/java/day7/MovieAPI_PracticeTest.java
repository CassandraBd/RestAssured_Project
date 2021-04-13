package day7;

import static io.restassured.RestAssured.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Movie;
import pojo.Rating;

import java.util.List;

public class MovieAPI_PracticeTest {

    // save the result of your request
    // Send GET  http://www.omdbapi.com/?t=Avenger&apikey=YOUR OWN API KEY goes here
    // save the response into Movie POJO , title Str, year int , Released str ,Language
    // ignore any unknown properties
    // match the json fields to pojo fields

    @DisplayName("GET http://www.omdbapi.com/?t=Avenger&apikey=YOUR OWN API KEY")
    @Test
    public void testMovieToPOJO(){

        Movie m1= given()
                          .baseUri("http://www.omdbapi.com")
                          .queryParam("apikey", "8e4cebed")
                          .queryParam("t", "Avenger").
                when()
                          .get()
                      //    .jsonPath()
                      //    .getObject("", Movie.class);
                          .as(Movie.class); // if don't need a path do it like this
        System.out.println("m1 = " + m1);
    }

    @DisplayName("GET Search for avenger and save Ratings field into List<Rating>")
    @Test
    public void testMovieRatingToPOJO(){
        List<Rating> allRatings = given()
                                         .baseUri("http://www.omdbapi.com")
                                         .queryParam("apikey", "8e4cebed")
                                         .queryParam("t", "Avenger").
                        when()
                                         .get()
                                         .jsonPath()
                                         .getList("Ratings", Rating.class);
        System.out.println("allRatings = " + allRatings);

    }
}
