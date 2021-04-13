package day8;

import org.junit.jupiter.api.Test;
import test_util.DB_Utility;
import test_util.LibraryAppBaseTest;

import static io.restassured.RestAssured.*;
import static test_util.DB_Utility.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Library_API_DB_TEST extends LibraryAppBaseTest {

    // 1. set up db connection in base test
    @Test
    public void testDashboardStatsNumbers(){

     //   DB_Utility.createConnection();
        runQuery("SELECT COUNT(*) FROM books");
    //    System.out.println(getFirstRowFirstColumn());

        String expectedBookCount =  getFirstRowFirstColumn()  ;

        runQuery("SELECT COUNT(*) FROM users ");
        String expectedUserCount =  getFirstRowFirstColumn()  ;

        runQuery("SELECT count(*) FROM book_borrow WHERE returned_date IS NULL ");
        String expectedBorrowedBookCount =  getFirstRowFirstColumn()  ;

        given()
                .header("X-LIBRARY-TOKEN", librarianToken).
        when()
                .get("/dashboard_stats").
        then()
                .log().body()
                .statusCode(200)
                .body("book_count" , is(expectedBookCount) )
                .body("borrowed_books" , is(expectedBorrowedBookCount) )
                .body("users", is(expectedUserCount))

        ;
    }
}
