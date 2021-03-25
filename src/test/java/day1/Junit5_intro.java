package day1;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

// setting display name of the test class in test result using @DisplayName
@DisplayName("Day 1 of JUnit5 Test")
public class Junit5_intro {

    @DisplayName("Testing numbers")
    @Test
    public void test(){
        System.out.println("Learning JUnit5");

        assertEquals(1,1);
        assertEquals(1,2, "Something is wrong!!");

    }

    @DisplayName("Testing name starting with A")
    @Test
    public void testName(){// add one more test to assert your name first character start with A
       String name = "Adnan";
     //   assertEquals('A', name.charAt(0));
        assertTrue(name.startsWith("A"));

    }

}
