package day3;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import pojo.Spartan;
import test_util.SpartanNoAuthBaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import test_util.SpartanNoAuthBaseTest;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;

@DisplayName("Testing adding data to Spartan app multiple way")
public class SpartanPostingData_Test extends SpartanNoAuthBaseTest {

    @DisplayName("POST /spartans with String")
    @Test
    public void testPostDataWithStringBody(){
        /*
        {
    "name" : "Ayah",
    "gender" : "Female",
    "phone" : 9876543210
        }
         */
        String postStrBody = " {\n" +
                "    \"name\" : \"Ayah\",\n" +
                "    \"gender\" : \"Female\",\n" +
                "    \"phone\" : 9876543210\n" +
                "        }";
        given()
                .log().all()
    //            .header("Content-Type", "application/json") first way
    //            .contentType("application/json") second way
                .contentType(ContentType.JSON) // this is providing header for request
                .body(postStrBody).
        when()
                .post("/spartans").
        then()
                .log().all()
                .statusCode(is(201))
                .contentType(ContentType.JSON) // this is asserting response
                .body("success", is("A Spartan is Born!"))
               .body("data.name", is("Ayah"));

    }

    @DisplayName("POST /spartans with external file")
    @Test
    public void testPostDataWithJsonFileAsBody() {
        // singleSpartan.json with below content
        /*
        {
    "name" : "Ayah",
    "gender" : "Female",
    "phone" : 9876543210
        }
         */
     File jsonFile = new File("singleSpartan.json");
     given()
             .log().all()
             .contentType(ContentType.JSON)
             .body(jsonFile).
     when()
             .post("/spartans").
     then()
             .log().all()
             .statusCode(201);

    }

    @DisplayName("POST /spartans with Map Object")
    @Test
    public void testPostDataWithMapObjectAsBody(){

         /*
        {
    "name" : "Ayah",
    "gender" : "Female",
    "phone" : 9876543210
        }
         */
        Map<String,Object> bodyMap = new LinkedHashMap<>();
        bodyMap.put("name","Ayah");
        bodyMap.put("gender","Female");
        bodyMap.put("phone",9876543210L);
        System.out.println("bodyMap = " + bodyMap);
        // We are expecting this Java Map object to be converted into Json String and send as body
        // initially it failed, RestAssured can now find any serializer to convert java object to json
        // we added Jackson-data-bind dependency in pom. so RestAssured can be used to make its conversion happen

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(bodyMap).
        when()
                .post("/spartans").
        then()
                .log().all()
                .statusCode(201);

    }

    @DisplayName("POST /spartans with POJO")
    @Test
    public void testPostDataWithPOJOAsBody(){
         Spartan sp = new Spartan("Ayah", "Female", 9876543210L);
        // turn into below
         /*
        {
    "name" : "Ayah",
    "gender" : "Female",
    "phone" : 9876543210
        }
         */
        System.out.println("sp = " + sp);

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(   sp   ).
                when()
                .post("/spartans").
                then()
                .log().all()
                .statusCode(201)
        ;







    }

}
