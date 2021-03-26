package test_util;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class SpartanNoAuthBaseTest {

    @BeforeAll
    public static void init(){
        // this will set the part of UrL at RestAssured
        RestAssured.baseURI = "http://100.26.232.232:8000";
        //  RestAssured.port = 8000; can do but dont need to
        RestAssured.basePath = "/api";
    }

    @AfterAll
    public static void cleanup(){
        RestAssured.reset();
    }

}
