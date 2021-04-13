package day9;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pojo.SpartanPOJO;
import test_util.DB_Utility;
import test_util.HR_ORDS_API_BaseTest;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


public class HR_Homework_Test extends HR_ORDS_API_BaseTest {

    // HOMEWORK : RUN QUERY  runQuery("SELECT * FROM REGIONS") save result as List of Map
    // Write a method to return above List of Map called getAllRegionListOfMap
    // Write a parameterized Test for GET /regions/{region_id}
    // Use getAllRegionListOfMap method as Method Source for your Parameterized Test

    @ParameterizedTest
    @MethodSource("getAllRegionListOfMap")
    public void testAllRegions(Map<String, String> rowMapArg){
        System.out.println("rowMapArg = " + rowMapArg);

     /* First way to do it
       List<String>regionname= given().pathParams("region_id",rowMapArg.get("REGION_ID"))

                .when().get("/regions/{region_id}")
                .then()
                .log().ifValidationFails()
                .extract().jsonPath().getList("items.region_name",String.class);

        assertThat(regionname.get(0),is(rowMapArg.get("REGION_NAME")));
      */
        // Second way to do it
        int expectedRegionID = Integer.parseInt( rowMapArg.get("REGION_ID") ) ;
        String expectedRegionName =  rowMapArg.get("REGION_NAME") ;
        given()
                .log().uri()
                .pathParam("region_id" , expectedRegionID).
        when()
                .get("/regions/{region_id}").
        then()
                .body("count" , is(1))
                .body("items[0].region_id" , equalTo(expectedRegionID))
                .body("items[0].region_name" , is(expectedRegionName));

    }

    public static List<Map<String, String>> getAllRegionListOfMap(){
        DB_Utility.runQuery("SELECT * FROM REGIONS");
        return DB_Utility.getAllRowAsListOfMap();
    }



}



