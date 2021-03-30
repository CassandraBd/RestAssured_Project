package spartan_util;

import com.github.javafaker.Faker;
import pojo.Spartan;

import java.util.LinkedHashMap;
import java.util.Map;

public class SpartanUtil {
    /*
      - Used to get valid Map object to represent post body for Post/spartans request
      - @return Map object with random name, gender, phone(5000000000 - 1000000000)
     */

    private static Faker faker = new Faker() ;

    public static Map<String,Object> getRandomSpartanMap(){

        Map<String,Object> bodyMap = new LinkedHashMap<>();
        bodyMap.put("name", faker.name().firstName());
        bodyMap.put("gender", faker.demographic().sex());
        bodyMap.put("phone", faker.number().numberBetween(5_000_000_000L, 1_000_000_000L));

        return bodyMap ;
    }

    /*
       - Create Spartan object with random field object
       - @ return Spartan object with random name, gender, phone(5000000000 - 1000000000)
     */

    public static Spartan getRandomSpartanPOJO(){

        Spartan sp = new Spartan();
        sp.setName(faker.name().firstName());
        sp.setGender(faker.demographic().sex());
        sp.setPhone(faker.number().numberBetween(5_000_000_000L, 9_999_999_999L));

        return sp;
    }

}
