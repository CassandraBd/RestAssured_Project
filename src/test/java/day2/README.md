Note Day 2
The Strength of RestAssured is method chaining to make all part of providing additional information before sending request , sending the request and verifying the response on one shot in BDD style of method chaining as below

The Structure of RestAssured Method Chaining

given() ....   --> RequestSpecification
.header  accept() contentType()
.queryParam
.pathParam
.body
.log
.auth..
when()       --->> RequestSender
.get() ------>> Response Object
.post()
.put()
.delete()
then()      -----> ValidatableResponse  
This is where assertion happen
.statusCode
.header   accept() contentType()
.body( matchers goes here)
.log

Previously we have sent a get request to GET /spartans/16 to get the data with spartan_id of 16. the number 16 here in rest api term is path parameter|variable

As above example of chaining , we can provide such information in given section of the chain

In Spartan app we can also ask for json result explicitly by providing accept header which can also be provided in given section

Below example show how it can be done in one Statement

Response r1 =
given()         // return RequestSpecification until when section start
.header("Accept", "application/json")
.pathParam("spartan_id",16).
when()          // RequestSender
.get("/spartans/{spartan_id}")  // return Response Object
.prettyPeek()
;
RestAssured has strong support for common headers like ContentType and Accept by providing ready methods

Above way of providing path parameter can be simplified as below by directly providing it in get method

Response r2 =
given()
// this is same as .header("Accept", "application/json")
.accept("application/json").
when()
// This is alternative way of providing
// path variable and value directly in get method
.get("/spartans/{spartan_id}" , 16 )
.prettyPeek() ; //return same response object after printing ,optional 