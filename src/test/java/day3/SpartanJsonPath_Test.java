package day3;

import io.restassured.path.json.JsonPath;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import test_util.SpartanNoAuthBaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import test_util.SpartanNoAuthBaseTest;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;

public class SpartanJsonPath_Test extends SpartanNoAuthBaseTest {


    //http:/100.26.232.232/.55:8000/api/spartans/3

    @Test
    public void getOneSpartan(){
     Response response =
        given()
                .pathParam("id","86").
        get("/spartans/{id}")
             .prettyPeek();
     
     // using path method to extract data
        int myId = response.path("id");
        System.out.println("myId = " + myId);

     // Few meaning of JsonPath:
     // 1. just like jPath -- it is used to provide location of certain data
     // 2. jsonPath as a class coming from RestAssured to provide reusable methods to extract data
     // 3. jsonPath() method of Response object to get JsonPath object
        JsonPath jp = response.jsonPath(); // number 2 for variable type, number 3 for method
        
        myId = jp.getInt("id");
        System.out.println("the result is = " + myId);
        
        long phoneNum = jp.getLong("phone");
        System.out.println("phoneNum = " + phoneNum);
        
        String myName = jp.getString("name");
        System.out.println("myName = " + myName);

        System.out.println("Saves whole json object into map = " + jp.getMap("")); // 1st way

        Map<String, Object> resultJsonInMap = jp.getMap(""); // 2nd way of doing it with a variable
        System.out.println("resultJsonInMap = " + resultJsonInMap);

    }

    @DisplayName("Extract data from GET /spartans")
    @Test
    public void testGetAllSpartans(){

    //    Response response = get("/spartans");
    //    JsonPath jp = response.jsonPath();
        JsonPath jp = get("/spartans").jsonPath()
                .prettyPeek(); // same way as the above 2

        // print first id in the json array response
        // [{}, {}, {}]
        System.out.println("jp.getInt(\"id[0]\") = " + jp.getInt("id[0]"));
        // print second name in json array response
        System.out.println("jp.getString(\"name[1]\") = " + jp.getString("name[1]"));

        System.out.println("jp.getMap(\"[0]\") = " + jp.getMap("[0]"));

        // print first id in json array response, same as first response
        System.out.println("jp.getInt(\"[0].id\") = " + jp.getInt("[0].id"));

    }
   // http://100.26.232.232:8000/api/spartans/search?nameContains=Abigale&gender=Male

   @ DisplayName("Extract data from GET /spartans/search ")
   @Test
   public void testGetSearchSpartans(){
       JsonPath jp =
        given()
                .queryParam("nameContains", "Albert")
                .queryParam("gender", "Male")
                .log().all().
        when()
                .get("/spartans/search")
                .prettyPeek()
                .jsonPath();

       // find out first gut id, second guy name
       // content[0].id         content[1].name
       System.out.println("jp.getInt(\"content[0].id\") = " + jp.getInt("content[0].id"));
       System.out.println("jp.getString(\"content[1].name\") = " + jp.getString("content[1].name"));

       // store first jsonObject into a map
       Map<String, Object> firstJsonInMap = jp.getMap("content[0]");
       System.out.println("firstJsonInMap = " + firstJsonInMap);

   }

   @DisplayName("Saving json array fields into List")
   @Test
   public void testSavingJsonArrayFieldsIntoList(){
       JsonPath jp =
               given()
                       .queryParam("nameContains", "Albert")
                       .queryParam("gender", "Male")
                       .log().all().
                       when()
                       .get("/spartans/search")
                       .prettyPeek()
                       .jsonPath();

       // save all the ids into a list
       System.out.println("jp.getList(\"content.id\") = " + jp.getList("content.id"));
       System.out.println("jp.getList(\"content.name\") = " + jp.getList("content.name"));
       System.out.println("jp.getList(\"content.phone\") = " + jp.getList("content.phone"));

       // getList method has 2 overloaded versions
       // 1. jp.getList("json path here"); --> the type of list will be automatically determined
       List<Integer> allIds = jp.getList("content.id");
       // 2. jp.getList("json path here"); class Type you want this list to have
       List<Integer> allIds2 = jp.getList("content.id", Integer.class);

       List<String> allNames = jp.getList("content.name");
       List<String> allNames2 = jp.getList("content.name", String.class);

       List<Integer> allPhone = jp.getList("content.phone");
       List<Long> allPhone2 = jp.getList("content.phone", Long.class);

   }

   @DisplayName("Get List Practice for GET/spartans")
   @Test
   public void testGetListOutOfAllSpartans(){
       JsonPath jp = get("/spartans").jsonPath();

       // save the list into List object and assert the size

       List<Integer> allIds = jp.getList("id", Integer.class);
       List<String> allNames = jp.getList("id", String.class);
       List<Long> allPhones = jp.getList("id", Long.class);

       assertThat(allIds, hasSize(132));
       assertThat(allNames, hasSize(132));
       assertThat(allPhones, hasSize(132));

   }

   // Practice jsonPath as much as you can, using all those requests we have sent in Postman
    /*
    for example :
     * GET http://www.omdbapi.com/?t=Superman&apiKey=YOUR KEY GOES HERE
     * save and print below information from the response using JsonPath
     *
     *  Title , Year , imdbRating  in correct data type
     *  Get second Ratings source
     *  Get first Ratings value
     *
     * GET http://www.omdbapi.com/?s=Flash&type=series&apiKey=YOUR KEY GOES HERE
     *
     *  save and print 3rd json object fields : Title , Year , imdbID
     *  save and print all of the json array imdbID in to List<String>
     *
     *  print totalResult field value
     *  the request is designed to only give you 10 results per page
     *  Optionally :
     *      send more request if the result is more than 10
     *
     *  eventually save all movie titles from all the pages into List<String>
     */

    @DisplayName("Test Open Movie DB")
    @Test
   public void test(){
     //   String apiKey = ConfigurationReader.getProperty("omdbKey");
        JsonPath jp =
                given()
                        .queryParam("t", "Superman")
                   //     .queryParam("apiKey", apiKey)
                        .log().all().
                        when()
                        .get("http://www.omdbapi.com")
                        .prettyPeek()
                        .jsonPath();

        System.out.println("jp.getString(\"Title\") = " + jp.getString("Title"));
        System.out.println("jp.getInt(\"Year\") = " + jp.getInt("Year"));
        System.out.println("jp.getDouble(\"imdbRating\") = " + jp.getDouble("imdbRating"));
    }
    @DisplayName("flash")
    @Test
    public void test2(){
     //   String apiKey = ConfigurationReader.getProperty("omdbKey");
        JsonPath jp =
                given()
                        .queryParam("s", "Flash")
                //        .queryParam("apikey", apiKey)
                        .queryParam("type", "series")
                        .log().all().
                        when()
                        .get("http://www.omdbapi.com")
                        .prettyPeek()
                        .jsonPath();
        System.out.println("jp.getString(\"Search.Title[2]\") = " + jp.getString("Search.Title[2]"));
        System.out.println("jp.get(\"Year\") = " + jp.getString("Search.Year[2]"));
        System.out.println("jp.get(\"imdbID\") = " + jp.getString("Search.imdbID[2]"));
    }

  /*  @DisplayName("practise 2")
    @Test
    public void practise2() {
        Response response =
                given()
                        .log().uri()
                        .queryParam("s", "Flash")
                        .queryParam("type", "series")
                        .queryParam("apiKey", key).
                        when()
                        .get("http://www.omdbapi.com")
                //.prettyPeek()
                ;
        JsonPath jp = response.jsonPath();

        System.out.println("jp.getString(\"Search[2].Title\") = " + jp.getString("Search[2].Title"));
        System.out.println("jp.getString(\"Search[2].Year\") = " + jp.getString("Search[2].Year"));
        System.out.println("jp.getString(\"Search[2].imdbID\") = " + jp.getString("Search[2].imdbID"));

        List<String> allImdbID = jp.getList("Search.imdbID", String.class);
        System.out.println("allImdbID = " + allImdbID);

        // OPTIONAL
        List<String> allTitles = jp.getList("Search.Title");
        int totalResults = jp.getInt("totalResults");
        System.out.println("totalResults = " + totalResults);
        int iterNum = totalResults / 10;
        if(iterNum > 1) {
            for (int i = 2; i <= iterNum; i++) {
                jp =
                        given()
                                .queryParam("s", "Flash")
                                .queryParam("type", "series")
                                .queryParam("page", i)
                                .queryParam("apiKey", key).
                                when()
                                .get("http://www.omdbapi.com")
                                .jsonPath();
                allTitles.addAll(jp.getList("Search.Title"));
            }
        }
        System.out.println("allTitles = " + allTitles);

   */
    }












