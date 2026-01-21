package addPlaceAPIClasses;

import io.restassured.RestAssured;

//since it's static so we need to manually import this package to use method 'equalTo()' 
import static org.hamcrest.Matchers.*;

import files.Payload;

//we need to manually import this package since it's static
import static io.restassured.RestAssured.*;

public class Basic_Original {

	public static void main(String[] args) {
		
//		validate if Add Place API is working correctly
//		Rest assured works on three principles
//		1) given() - all input details except resource and http methods
//		2) when() - submit the API (resource and http methods)
//		3) then() - valdiate the response
		
//		Sets the common base URL for all API calls in this test.
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
//		to paste the json in .body(), follow the steps
//		1) write this .body("")
//		2) then inside paste the json body so eclipse will convert it into string format automatically
		
//		log().all()->in request , it logs everything about the request (URL,Headers,Query params,Body)
		
		given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		
//		json is stored in a separate class so the code doesn't look messy
		.body(Payload.AddPlace())
		
		.when().post("maps/api/place/add/json")
		
//		log().all()-> in response, it logs full response(Status line,Headers,Body)
		
/*
 * 		assertThat() in Rest Assured is used to start validations on the response. It improves readability and allows chaining of 
 *		multiple assertions like status code, body, headers, and response time.
*/		
		.then().log().all().assertThat().statusCode(200)
		
//		validating the field in the input response
		.body("scope", equalTo("APP"))
		
//		validating the Server in the headers of response
		.header("Server", "Apache/2.4.52 (Ubuntu)");
		
		


	}

}
