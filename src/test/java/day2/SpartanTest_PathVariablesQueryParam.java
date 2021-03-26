package day2;

import test_util.SpartanNoAuthBaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import test_util.SpartanNoAuthBaseTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;

@DisplayName("Spartan Test with path variable and query param")
public class SpartanTest_PathVariablesQueryParam extends SpartanNoAuthBaseTest {

    @Test
    public void getOneSpartan(){
       // get("/spartans/16").prettyPeek();

        // I want to provide 16 as path variable|parameter
        // I want to provide accept header
        Response r1 =
                given()
                .header("Accept", "application/json")
                .pathParam("spartan_id", "16").
        when()
                .get("/spartans/{spartan_id}")
                .prettyPeek() ;

        Response r2 =
                given()
                // this is same as  .header("Accept", "application/json")
                .accept("application/json").
        when()
                // This is alternative way of providing
                // path variable and value directly in get method
                .get("/spartans/{spartan_id}", 16)
                .prettyPeek();

    }

    @DisplayName("logging the request")
    @Test
    public void getOneSpartanWithLog(){
        Response response =
                given()
                          .log().all() // this will log the response
                      //   ### Logging the Request
                      //   You can log each and every part of the request in the console by adding log
                      //   level in `given` part of the request.
                     //   .log().uri() // just for the request url
                     //   .log().body() // for logging request body
                     //   .log().params() // logging only request parameters
                     //   .log().method() // just log the http method
                     //   .log().ifValidationFails() // only log the request if validation in the section has failed
                     //   .log().headers()
                     //   .log().ifError // anything not 2xx status is seen as error for this method
                     //   .log()ifStatusCodeIsEqualTo(200)
                        .accept("application/json")
                        .pathParam("id", 16).
                when()
                        .get("/spartans/{id}")
                        .prettyPeek();

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.contentType(), is("application/json"));
        assertThat(response.path("name"),is("Sinclair"));
    }
}
