package day4;

import spartan_util.SpartanUtil;
import test_util.SpartanNoAuthBaseTest;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import pojo.Spartan;
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

public class SpartanRandomPOST_Test extends SpartanNoAuthBaseTest {

    @DisplayName("/POST /spartans with random Data")
    @Test
    public void addOneRandomSpartanTest() {
        // this is the mao object we sent as body , it's expected result
        Map<String, Object> randomRequestBodyMap
                = SpartanUtil.getRandomSpartanMap();

        given()
                .log().body()
                .contentType(ContentType.JSON)
                .body(randomRequestBodyMap).
                when()
                .post("/spartans").
                then()
                .log().all()
                .statusCode(is(201))
                .body("data.name", is(randomRequestBodyMap.get("name")))
                .body("data.gender", is(randomRequestBodyMap.get("gender")))
                .body("data.phone", is(randomRequestBodyMap.get("phone")));
    }

    @DisplayName("/POST/spartans with random Spartan POJO")
    @Test
    public void addOneRandomSpartanPOJOTest(){
        Spartan randomPOJO = SpartanUtil.getRandomSpartanPOJO();

        given()
                .log().body()
                .contentType(ContentType.JSON)
                .body( randomPOJO ).
                when()
                .post("/spartans").
                then()
                .log().all()
                .body("data.name" ,  is( randomPOJO.getName() ) )
                .body("data.gender" ,  is( randomPOJO.getGender() ) )
                .body("data.phone" ,  is( randomPOJO.getPhone() ) );
    }


}