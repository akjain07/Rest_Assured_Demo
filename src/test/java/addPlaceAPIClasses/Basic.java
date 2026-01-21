package addPlaceAPIClasses;

import io.restassured.RestAssured;

//since it's static so we need to manually import this package to use method 'equalTo()' 
import static org.hamcrest.Matchers.*;

import files.Payload;

//we need to manually import this package since it's static
import static io.restassured.RestAssured.*;

public class Basic {

	public static void main(String[] args) {
		

		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body(Payload.AddPlace())		
		
		.when().post("maps/api/place/add/json")
		
		.then().log().all().assertThat().statusCode(200)
		.body("scope", equalTo("APP"))
		.header("Server", "Apache/2.4.52 (Ubuntu)");


	}

}
