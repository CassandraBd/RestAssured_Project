package day10;

import io.restassured.path.xml.XmlPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class MovieAPI_XML_Test {

    @DisplayName("GET movie attributes in xml")
    @Test
    public void testAttributes() {
        XmlPath xp =
                given()
                        .baseUri("http://www.omdbapi.com")
                        .queryParam("apikey","8e4cebed")
                        .queryParam("t","Superman")
                        .queryParam("r", "xml").
                when()
                        .get().xmlPath();
        String title = xp.getString("root.movie.@title") ;
        System.out.println("title = " + title);
    }

    @DisplayName("Get movies attributes in xml")
    @Test
    public void testAllMovieAttributes(){

        XmlPath xp = given()
                .baseUri("http://www.omdbapi.com")
                .queryParam("apikey","8e4cebed")
                .queryParam("s","WandaVision")
                .queryParam("r","xml").
                        when()
                .get()
                .xmlPath() ;
        List<String> allTitles = xp.getList("root.result.@title", String.class);
        System.out.println("allTitles = " + allTitles);


    }
}
