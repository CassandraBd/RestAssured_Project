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

Examples we have tried.

Nunber Related
assertThat(5+5, is(10) ) ;
assertThat(5+5, equalTo(10) );
assertThat(5+5 , is( equalTo(10) ) );

// negative assertion  5+5 is not 11
assertThat(5+5, not(11) );
assertThat(5+5, is( not(11) )   );
assertThat(5+5, is(not(equalTo(11))));
//        greaterThan()
//        lessThan()
//        greaterThanOrEqualTo()
//        lessThanOrEqualTo()
assertThat(5+5, is( greaterThan(9))  );
assertThat(5+5, is( lessThan(19))  );
String related
String msg = "B21 is learning Hamcrest" ;

// checking for equality is same as numbers above
assertThat(msg , is("B21 is learning Hamcrest") );
assertThat(msg , equalTo("B21 is learning Hamcrest") );
assertThat(msg , is(equalTo("B21 is learning Hamcrest")) );

// check if this msg start with B21
assertThat(msg, startsWith("B21"));
// now do it in case insensitive manner
assertThat(msg, startsWithIgnoringCase("b21") );
// check if the msg end with rest
assertThat(msg, endsWith("rest") );

// check if msg contains String learning
assertThat(msg, containsString("learning"));
assertThat(msg, containsStringIgnoringCase("LEARNING"));

String str = "   " ;
// check if above str is blank
assertThat(str, blankString() );
// check if trimmed str is empty String
assertThat(str.trim()  , emptyString()  );
Collection Related
List<Integer> lst = Arrays.asList(1,4,7,3,7,44,88,99,44) ;

// checking the side of this list
assertThat(lst, hasSize(9) );
// check if this list hasItem 7
assertThat(lst, hasItem(7) );
// check if this list hasItems 7,99,88
assertThat(lst, hasItems(7,99,88) );

// check if every item in this list is greaterThan 0
assertThat(lst, everyItem( greaterThan(0) ) );

RestAssured Intro
Dependency for Java RestAssured library

<dependency>
      <groupId>io.rest-assured</groupId>
      <artifactId>rest-assured</artifactId>
      <version>4.3.3</version>
      <scope>test</scope>
</dependency>
Add below 3 static imports for all RestAssured related classes.

import static io.restassured.RestAssured.* ;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.* ;
Sending Request & Saving Response
Nearly all fields and methods of the class RestAssured are static, so static import will give us access to all fields and methods directly.

Sending a get request and saving the response :

// get method is coming from RestAssured class
// type Response is coming from  io.restassured.response.Response;
// so first step import
Response response = get("Your request URL here") ;
Many methods available in Response objects to extract information out of the object

statusCode() or getStatusCode()
getHeader("header name") or header("Header name") for getting any header
getContentType() or contentType() for specifically getting contentType header
asString() to get the response body as String
there are other ways do same thing
prettyPrint()
print out the body with proper indentation if required and return Sting
prettyPeek()
print out the body with proper indentation if required and return same Response Object
path("the path to get json field | xml node )
{
"id": 16,
"name": "Wonder Woman",
"gender": "Female",
"phone": 9234567890
}
response.path("id") will return 16
response.path("name") will return Wonder Woman
and so on
System.out.println("response.statusCode() = "
+ response.statusCode() );
System.out.println("response.getStatusCode() = "
+ response.getStatusCode());

// getting specific header
System.out.println("response.getHeader(\"Content-Type\") = " + response.getHeader("Content-Type")    );
// getting content type header using ready method
System.out.println("response.contentType() = " + response.contentType() );
System.out.println("response.getContentType() = " + response.getContentType());
// getting body as String
System.out.println("response.asString() = " + response.asString());