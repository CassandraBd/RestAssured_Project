package day7;

import pojo.SpartanPOJO;
import spartan_util.SpartanUtil;
import test_util.SpartanNoAuthBaseTest;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import pojo.Spartan;
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

public class SpartanDeserialization_Test extends SpartanNoAuthBaseTest {

    // Serialization - Java Object to Json(or any other type of text)
    // De-Serialization - Json(text) to Java

    @DisplayName("GET /spartans/{id}")
    @Test
    public void testGetOneData() {

        given()
                .pathParam("id", 100).
                when()
                .get("/spartans/{id}").
                then()
                .statusCode(200)
                .log().body();
        
        // Send same request, store the result into SpartanPOJO object
       Response response = 
        given()
                .pathParam("id", 100).
        when()
                .get("/spartans/{id}");

        SpartanPOJO sp = response.as(SpartanPOJO.class);
        System.out.println("sp = " + sp);
        
        // second way to do it
        JsonPath jp = response.jsonPath();
        SpartanPOJO sp1 = jp.getObject("", SpartanPOJO.class);
        System.out.println("sp1 = " + sp1);
    }

    @DisplayName("GET /spartans/search")
    @Test
    public void testSearch(){
      //  /spartans/search?nameContains=Mustafa&gender=Male
     //   send get request to above endpoint and save first object with type SpartanPOJO
       Response response =
        given()
                .log().uri()
                .queryParam("nameContains", "a")
                .queryParam("gender", "Male").
        when()
                .get("/spartans/search").prettyPeek();
        
        // response.as will not work here because we need to provide
        // path to get to the json object we want content[0]
        JsonPath jp = response.jsonPath();
        SpartanPOJO sp = jp.getObject("content[0]", SpartanPOJO.class);
        System.out.println("sp = " + sp);
        
        // this is how we can do whole thing in one chain, prints same result
        SpartanPOJO sp1 =
                given()
                        .log().uri()
                        .queryParam("nameContains", "a")
                        .queryParam("gender", "Male").
                when()
                        .get("/spartans/search")
                        .jsonPath()
                        .getObject("content[0]", SpartanPOJO.class);
        System.out.println("sp1 = " + sp1);
    }

    @DisplayName("GET /spartans/search and save as List<SpartanPOJO>")
    @Test
    public void testSearchSaveList(){
        //  /spartans/search?nameContains=Mustafa&gender=Male
        //   send get request to above endpoint and save the json array into List<SpartanPOJO>
        List<SpartanPOJO> lst = given()
                                        .queryParam("nameContains", "a")
                                        .queryParam("gender", "Male").
                                when()
                                        .get("/spartans/search")
                                        .jsonPath()
                                        .getList("content", SpartanPOJO.class);
                                     // something.class return type class to specify what kind of Item you want to have in your list
        System.out.println("lst = " + lst);
        lst.forEach(blabla -> System.out.println(blabla));
        

    }


}
