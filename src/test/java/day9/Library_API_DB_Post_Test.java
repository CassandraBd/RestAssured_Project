package day9;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test_util.DB_Utility;
import test_util.LibraryAppBaseTest;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Library_API_DB_Post_Test extends LibraryAppBaseTest {

    // Add a random book using POST /add_book
    // grab the id  and write a query to book information with this id
    // assert ALL data match exactly as it was posted.

    @DisplayName("Add One book ,Validate from DB")
    @Test
    public void testAddBookPersisted(){
        Map<String, Object> randomBookMapBody = getRandomBook();
        System.out.println("randomBook = " + randomBookMapBody);

        int newBookId =  given()
                .header("X-LIBRARY-TOKEN" , librarianToken)
                .contentType(ContentType.URLENC)
                .formParams(randomBookMapBody).
         when()
                .post("/add_book").
         then()
                // normally it return 201 , this one decided to return 200
                .statusCode(200)
                .log().body()
                .extract()
                .jsonPath().getInt("book_id") ;
        System.out.println("newBookId = " + newBookId);

        DB_Utility.runQuery("SELECT * FROM books where id = "+newBookId);
    //    DB_Utility.displayAllData();
        Map<String, String> dbResultMap = DB_Utility.getRowMap(1);
        System.out.println("dbResultMap = " + dbResultMap);

    //  randomBook = {year=1686, author=Verona Konopelski, isbn=38406063, name=The Far-Distant Oxus, description=Quantum cryptography does not work on Chuck Norris. When something is being observed by Chuck it stays in the same state until he's finished., book_category_id=14}
    //  dbResultMap = {id=3148, name=The Far-Distant Oxus, isbn=38406063, year=1686, author=Verona Konopelski, book_category_id=14, description=Quantum cryptography does not work on Chuck Norris. When something is being observed by Chuck it stays in the same state until he's finished., added_date=2021-04-09 18:54:44}
        assertThat(dbResultMap.get("name") , is(randomBookMapBody.get("name"))   ) ;
    // keep going and do the rest , or find a better way.




    }
}
