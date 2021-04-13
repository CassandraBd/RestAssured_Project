package test_util;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import pojo.Spartan;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class SpartanNoAuthBaseTest {

    @BeforeAll
    public static void init(){
        // as a homework , put these information
        // in configurations.properties file
        // this will set the part of UrL at RestAssured
        RestAssured.baseURI = "http://100.26.232.232:8000";
        //  RestAssured.port = 8000; can do but dont need to
        RestAssured.basePath = "/api";

        String url = ConfigurationReader.getProperty("spartan.database.url");
        String username = ConfigurationReader.getProperty("spartan.database.username");
        String password = ConfigurationReader.getProperty("spartan.database.password");
        DB_Utility.createConnection(url,username,password);
    }

    @AfterAll
    public static void cleanup(){

        RestAssured.reset();
        DB_Utility.destroy();
    }



}
