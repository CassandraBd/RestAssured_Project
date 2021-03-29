Extracting response data using JsonPath
To make it clear what we refer when we talk about the word json path , let's see few places this word comes up : Few meaning of JsonPath :

just like xpath -- it is used to provide location of certain data
JsonPath as a class coming from RestAssured to provide reusable methods to extract data
jsonPath() method of Response object to get JsonPath object
It's easy to get JsonPath object from response using jsonPath() method.

// any valid request to get response will work,
// for simplicity here we are just using get
Response response = get("/spartans/160") ;
JsonPath jp = response.jsonPath();
// or in one shot
// JsonPath jp = get("/spartans/160").jsonPath();

int myId       = jp.getInt("id") ; //160
String myName  = jp.getString("name") ; //B21 user
String myGender= jp.getString("gender") ; //Male
long mylong    = jp.getLong("phone") ; //9172288772
// json path to get the whole json object is empty String since it's at root level
Map<String, Object> resultMap = jp.getMap("");
//resultMap  :  {id=160, name=B21 user, gender=Male, phone=9172288772}
Here is the simple json response for this request without any nested json

{
"id": 160,
"name": "B21 user",
"gender": "Male",
"phone": 9172288772
}
Data in Json array can be retrieved using index , JsonPath object also have support for saving the field values into the list

for example :

JsonPath jp = get("/spartans").jsonPath() ;

int firstID = jp.getInt("[0].id")
// or  jp.getInt("id[0]")
// getting second object name
String secondName  = jp.getString("name[1]")

List<Integer> allIds = jp.getList("id" , Integer.class) ;
List<String> allNames = jp.getList("name" , String.class) ;
List<Long> allPhones = jp.getList("phone" , Long.class) ;
Result

[
{
"id": 33,
"name": "Wilek",
"gender": "Male",
"phone": 2877865902
},
{
"id": 34,
"name": "Tucky",
"gender": "Male",
"phone": 2935099804
},
{
"id": 35,
"name": "Gardiner",
"gender": "Male",
"phone": 3751113352
}
]
More detailed examples can be found here