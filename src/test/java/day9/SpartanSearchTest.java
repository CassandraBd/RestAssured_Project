package day9;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import test_util.DB_Utility;
import test_util.SpartanNoAuthBaseTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class SpartanSearchTest extends SpartanNoAuthBaseTest {

    @DisplayName("Test GET /spartans/search result with DB result")
    @Test
    public void testSearch(){
        // nameContains a and gender Female
       String query = "SELECT * FROM SPARTANS WHERE LOWER(NAME) LIKE '%a%' and GENDER = 'Female'";
        DB_Utility.runQuery(query);
    //    DB_Utility.displayAllData();
        int expectedCount = DB_Utility.getRowCount();
        System.out.println("count = " + expectedCount);
        given()
                .queryParam("nameContains", "a")
                .queryParam("gender", "Female").
        when()
                .get("/spartans/search").
        then()
                .statusCode(200)
                .body("totalElement", equalTo(expectedCount))
            //   content jsonpath is pointing to json array, so we can directly check the size using hasSize matcher
                .body("content", hasSize(expectedCount));
    }

    @ParameterizedTest
    @CsvSource({
            "e, Male",
            "le, Female",
            "k, Male",
            "g, Male",
            "u, Female",
            "f, Male"
    })
    public void testSearchParameterized(String nameArg, String genderArg) {

        String query = "SELECT * FROM SPARTANS WHERE LOWER(NAME) LIKE '%" +nameArg+ "%' and GENDER = '" +genderArg+ "'";
        System.out.println("query = " + query);
        DB_Utility.runQuery(query);

        int expectedCount = DB_Utility.getRowCount();
        System.out.println("expectedCount = " + expectedCount);
        given()
                .queryParam("nameContains", nameArg)
                .queryParam("gender", genderArg).
        when()
                .get("/spartans/search").
        then()
                .statusCode(200)
                .body("totalElement", equalTo(expectedCount))
            //  Optionally, continue from here and check
           //   If every item name containsString ignore case what we search for
                .body("content.name", everyItem(containsStringIgnoringCase(nameArg)))
           //   every item is what we search for
                .body("content.gender", everyItem(is(genderArg)))




        ;


    }
}
/*
   Another way to do it
   int totalElements =
        given()
                .log().uri()
                .queryParam("nameContains", "a")
                .queryParam("gender", "Female")
       .when()
                .get("/spartans/search")
       .then()
                .log().all()
                .statusCode(200).extract().jsonPath().getInt("totalElement");
        System.out.println("totalElements = " + totalElements);

        runQuery("Select * From SPARTANS where NAME like '%a%' AND GENDER = 'Female'");
        int totalNumberOfElementsInDB = getRowCount();
        assertThat(totalNumberOfElementsInDB, is(equalTo(totalElements)));
 */