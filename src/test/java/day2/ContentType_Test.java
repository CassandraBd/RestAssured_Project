package day2;

import io.restassured.http.ContentType;
import test_util.SpartanNoAuthBaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import test_util.SpartanNoAuthBaseTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;

@DisplayName("Spartan ContentType Summary")
public class ContentType_Test extends SpartanNoAuthBaseTest {

    @DisplayName("GET /hello")
    @Test
    public void testHelloContentType(){
        when()
                .get("/hello").
        then()
                .contentType(ContentType.TEXT)
                .body(is("Hello from Sparta"));

    }

    @DisplayName("GET /spartans in json")
    @Test
    public void testSpartansJsonContentType(){
        given()
                .accept(ContentType.JSON). // asking json result from the server
        when()
                .get("/spartans") .
        then()
        .contentType(ContentType.JSON);  // verifying the response body is json
    }

    @DisplayName("GET /spartans in xml")
    @Test
    public void testSpartansXmlContentType(){
        given()
                .accept(ContentType.XML). // asking xml result from the server
                when()
                .get("/spartans") .
                then()
                .contentType(ContentType.XML); // verifying the response body is xml

    }

}
