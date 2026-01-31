package addPlaceAPI_SpecBuilder;

import static io.restassured.RestAssured.given;
import java.util.ArrayList;
import java.util.List;
import addPlaceAPI_POJOClasses.AddPlace_POJO;
import addPlaceAPI_POJOClasses.locationPOJO;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class AddPlaceApi_SpecBuilder {

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
		
		
//		This block creates a template for all requests to this API.
		RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.addQueryParam("key","qaclick123")
		.setContentType(ContentType.JSON)
		.build();
		
		ResponseSpecification resSpec=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
//		req1 stores the complete request definition — base URI, headers, query params, and body — and is ready to be executed.
		RequestSpecification req1=given().spec(reqSpec).body(app);
		
//		response represents the actual HTTP response returned by the server after a request is sent.
		Response response = req1.when().post("maps/api/place/add/json")	
		.then().log().all().spec(resSpec)
		.extract().response();
		
		String resString =response.asString();
		System.out.println(resString);
		
		
//		EXISTING code
		
//		given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
//		.body(app)		
//		.when().post("maps/api/place/add/json")	
//		.then().log().all().assertThat().statusCode(200)
//		.extract().response().asString();

	}

}
