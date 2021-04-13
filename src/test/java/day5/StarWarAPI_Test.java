package day5;

import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;

public class StarWarAPI_Test {
    /*
    Interview Questions :
    Send request to  GET https://swapi.dev/api/people/
    Find out average height of all people showed up in the response
     */

    @BeforeAll
    public static void init(){
        baseURI = "https://swapi.dev";
        basePath = "/api";
    }

    @DisplayName("GET average height from GET /people response")
    @Test
    public void testGetAverageHeight(){
        List<Integer> allHeights = get("/people")
                                       .jsonPath()
                                       .getList("results.height", Integer.class);
      //  allHeights.forEach(System.out::println); other ways to print
      //  listOfAllHeights.forEach(p-> System.out.println(p));
        System.out.println("allHeights = " + allHeights);
     //   from here it's all java
        int total = 0;
        for (Integer height : allHeights) {
            total+= height;
        }
        int average = total/(allHeights.size());
        System.out.println("average = " + average); // average = 159

    }

    // Above code will only retrieve first page that includes 10 people
    // but we have more than 10 people in star wars
    // we can get total count of people in first response count field
    // the decide how many page we have to go through by sending more request
    // then loop through the rest of the pages to add all heights to the list
    // and calculate the average from final list
    // in order to go to the next page we can use
    // page query parameter to decide which page we want to see

    // Here is the steps:
    // create an empty Integer empty list
    // Send GET /people -->
    // capture the total count using jsonPath
    // save first page heights into the list

    // Loop: from page 2 till last page
    // get the list of height integer using jsonPath
    // add this to the big list

    @DisplayName("Get all heights from all the pages and find average")
    @Test
    public void testGetAllPagesAverageHeight(){
        List<Integer> allHeights = new ArrayList<>();

        // send initial request, find total count and decide how many pages exists
        JsonPath jp = get("/people").jsonPath();
        int peopleCount = jp.getInt("count");
       // if there is remainder we want to add 1, if there is not keep it as is
       // like this in ternary
        int pageCount = (peopleCount % 10==0) ? peopleCount/10 : (peopleCount/10)+1;
      /*  if(peopleCount%10==0){
            pageCount = peopleCount/10;
        }else{
            pageCount = peopleCount/10+1;
        }
        another way to do it
       */

        List<Integer> firstPageHeights = jp.getList("results.height");
        allHeights.addAll(firstPageHeights);

      // now it's time to loop and get the rest of the pages
        for (int pageNum = 2; pageNum < pageCount; pageNum++) {
      // GET / people?page = yourPageNumberGoesHere
            List<Integer> heightsOnThisPage = get("/people?page="+pageNum)
                                              .jsonPath()
                                              .getList("results.height");
            allHeights.addAll(heightsOnThisPage);

        }
        System.out.println("allHeights = " + allHeights);
    }

    @AfterAll
    public static void cleanup(){
        reset();
    }



}
