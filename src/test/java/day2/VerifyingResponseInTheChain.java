package day2;

import test_util.SpartanNoAuthBaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import test_util.SpartanNoAuthBaseTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;


public class VerifyingResponseInTheChain extends SpartanNoAuthBaseTest {

    @DisplayName("Verifying the GET/spartans/{id} response directly in the chain")
    @Test
    public void testOneSpartanInOneShot(){

        given()
                .log().all()
                .pathParam("id", 16).
        when()
                .get("/spartans/{id}").
        then()
                .log().all() // this will log the response
                .statusCode(200)
                .header("Content-Type", is("application/json"))
                .contentType("application/json")
                .body("id", equalTo(16))
                .body("name", is("Sinclair"))
                .body("gender", is("Male"))
                .body("phone", equalTo(9714460354L));

    }
}
