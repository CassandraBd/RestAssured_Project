Note Day 1
JUnit 5
Here is the link for official documentation

Unlike previous versions of JUnit, JUnit 5 is composed of several modules from three different sub-projects.

JUnit 5 = JUnit Platform + JUnit Jupiter + JUnit Vintage

The JUnit Platform serves as a foundation for launching testing frameworks on the JVM. It also defines the TestEngine API for developing a testing framework that runs on the platform. Furthermore, the platform provides a Console Launcher to launch the platform from the command line and a JUnit 4 based Runner for running any TestEngine on the platform in a JUnit 4 based environment.

JUnit Jupiter is the combination of the new programming model and extension model for writing tests and extensions in JUnit 5. The Jupiter sub-project provides a TestEngine for running Jupiter based tests on the platform.

JUnit Vintage provides a TestEngine for running JUnit 3 and JUnit 4 based tests on the platform.

Maven Dependency
<dependency>
<groupId>org.junit.jupiter</groupId>
<artifactId>junit-jupiter</artifactId>
<version>5.7.1</version>
</dependency>
Assertions
assertEquals(actual , expected)
assertTrue( some boolean )
and so on...
Annotations
import static org.junit.jupiter.api.*

@Test
@DisplayName("Your custom name goes here)
@BeforeAll
This method must be static void
Run only once before each test class
@BeforeAll
public static void init(){
System.out.println("Before all is running");
}
@AfterAll
This must be static void as well
Run only once after all test
@BeforeEach
This is a void method
Run before each and every test
@AfterEach
This is a void method
Run after each and every test
@Disabled
for temporarily ignore the test
Before all is running
Before Each is running
Test 1 is running
After Each is running
Before Each is running
Test 2 is running
After Each is running
After all is running
Hamcrest Matchers Library
It's library to make the assertions more readable , it can be used along with any unit testing framework like Junit or TestNG

It's heavily used in RestAssured for assertions. RestAssured dependecy already contains all hamcrest related dependency so we can just directly use the rest assured dependency to access all hamcrest stuff.

<dependency>
      <groupId>io.rest-assured</groupId>
      <artifactId>rest-assured</artifactId>
      <version>4.3.3</version>
      <scope>test</scope>
</dependency>
If you wanted to hamcrest outside context of RestAssured you can add dependency separately

<dependency>
    <groupId>org.hamcrest</groupId>
    <artifactId>hamcrest</artifactId>
    <version>2.2</version>
</dependency>
Hamcrest assertions examples :
Static imports need to be added

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
Hamcrest Assertion make test readable for example if you wanted to check if 5+5 is 10

    @Test
    public void simpleTest1(){
        assertThat(5+5, is(10) ) ;
        assertThat(5+5, equalTo(10) );
    }
is(10) and equalTo(10) are 2 examples of Hamcrest Matchers available from vast varity of human readable Matchers you can find from Hamcrest Library

Here is the link for java doc with examples on how it works