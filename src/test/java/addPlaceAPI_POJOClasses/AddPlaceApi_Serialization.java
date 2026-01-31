package addPlaceAPI_POJOClasses;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;

public class AddPlaceApi_Serialization {

	public static void main(String[] args) {
		

		RestAssured.baseURI="https://rahulshettyacademy.com";
		
//		*********************** ADD PLACE API ***********************
		
//		creating the java object and setting the values to create the request payload
		AddPlace_POJO app=new AddPlace_POJO();
		app.setAccuracy(50);
		app.setName("Frontline house");
		app.setPhone_number("(+91) 983 893 3937");
		app.setAddress("29, side layout, cohen 09");
		app.setWebsite("http://google.com");
		app.setLanguage("French-IN");
		
		List<String> myList =new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		app.setTypes(myList);
		
		locationPOJO loc=new locationPOJO();
		loc.setLat(54.6);
		loc.setLng(-34);
		app.setLocation(loc);
		
//		Serialization of Json body into a java object
		
		given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body(app)		
		.when().post("maps/api/place/add/json")	
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		

	}

}
