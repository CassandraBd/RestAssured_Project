Note Day 4
#POST Practice with Random Data

Create Utility Class with 2 Utility methods to create random Spartan POST body
Method Should return Random Map<String,Object> with 3 fields
Method should return Spartan Object with random valid fields
package spartan_util;

import com.github.javafaker.Faker;
import pojo.Spartan;

import java.util.LinkedHashMap;
import java.util.Map;

public class SpartanUtil {

    private static Faker faker = new Faker();

    /**
     * Used to get valid Map object to represent post body for POST /spartans Request
     * @return Map object with Random name , gender , phone(5_000_000_000L - 10_000_000_000L)
     */
    public static Map<String,Object> getRandomSpartanMap(){

        Map<String,Object> bodyMap = new LinkedHashMap<>();
        bodyMap.put("name", faker.name().firstName()   );
        bodyMap.put("gender", faker.demographic().sex()  );
        bodyMap.put("phone", faker.number().numberBetween(5_000_000_000L, 10_000_000_000L  ) ) ;

        return bodyMap ;
    }

    /**
     * Create Spartan object with random field value
     * @return Spartan object with  Random name , gender , phone(5_000_000_000L - 10_000_000_000L)
     */
    public static Spartan getRandomSpartanPOJO(){

        Spartan sp = new Spartan() ;
        sp.setName( faker.name().firstName()  );
        sp.setGender( faker.demographic().sex() );
        sp.setPhone(  faker.number().numberBetween(5_000_000_000L, 10_000_000_000L  ) );

        return sp;
    }


}
Now we can use it in our POST request as below

Random Map
@DisplayName("/POST /spartans with random Data")
@Test
public void addOneRandomSpartanTest(){
// this is the map object we sent as body , it's expected result
Map<String, Object> randomRequestBodyMap
= SpartanUtil.getRandomSpartanMap() ;

        given()
                .log().body()
                .contentType(ContentType.JSON)
                .body(randomRequestBodyMap).
        when()
                .post("/spartans").
        then()
                .log().all()
                .statusCode( is(201) )
                .body("data.name" , is( randomRequestBodyMap.get("name") )   )
                .body("data.gender", is(randomRequestBodyMap.get("gender") ) )
                .body("data.phone", is(randomRequestBodyMap.get("phone") ) )
        ;
    }
Random POJO example
@DisplayName("/POST /spartans with random Spartan POJO")
@Test
public void addOneRandomSpartanPOJOTest(){
// this spartan object is request body and expected data from the response
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
                .body("data.phone" ,  is( randomPOJO.getPhone() ) )
        ;



    }
Here is the full class