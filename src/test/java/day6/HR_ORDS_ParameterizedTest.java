package day6;


import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import test_util.HR_ORDS_API_BaseTest;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
public class HR_ORDS_ParameterizedTest extends HR_ORDS_API_BaseTest{
    // GET http://100.26.232.232:1000/ords/hr/api
    // base_uri http://100.26.232.232:1000
    // base_path = /ords/hr/api
    // resources /counties/{country_id}

    @Test
    public void testCountries(){
        get("/countries/AR")
        .prettyPeek();
    }

    @ParameterizedTest
    @ValueSource(strings = {"AR", "AU", "US"})
    public void testSingleCountryWithValues(String countryIdArg){
        // GET/countries/{country_id}
        given()
                .log().uri()
                .pathParam("country_id", countryIdArg).
        when()
                .get("/countries/{country_id}").
        then()
                .log().body()
                .statusCode(200)
                .body("count", is(1));
    }
    @ParameterizedTest
    @CsvSource({"AR, Argentina",
            "US, United States of America",
            "UK, United Kingdom"})
    public void testingCountryWithCSVSource(String countryIdArg, String countryNameArg){
        // send request to GET /countries/{country_id}
        // expect country name to match the corresponding country id
        given()
                .log().uri()
                .pathParam("country_id", countryIdArg).
        when()
                .get("/countries/{country_id}").
        then()
                .body("items[0].country_name", is(countryNameArg) );
    }

    @ParameterizedTest
    @MethodSource("getManyCountryIds")
    public void testCountryWithMethodSource(String countryIdArg){
        System.out.println("countryIdArg = " + countryIdArg);
    }

    // write static method that return list of country ids
    public static List<String> getManyCountryIds(){
    //    List<String> countryNameList = Arrays.asList("AR", "BE","US") ;

    // send Request to GET /countries and save the country_id as List<String>
        List<String> countryNameList = get("/countries")
                                       .jsonPath().getList("items.country_id", String.class);

        return countryNameList ;


    }

}
