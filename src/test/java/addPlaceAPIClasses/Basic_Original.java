package addPlaceAPIClasses;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

//since it's static so we need to manually import this package to use method 'equalTo()' 
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;
import files.Reusable;

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
		
//		*********************** ADD PLACE API ***********************
		
		String response = given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		
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
		.header("Server", "Apache/2.4.52 (Ubuntu)")
		
//		is used in Rest Assured to extract the full HTTP response and convert its body into a String so that values 
//		can be parsed and reused in further test steps.
		.extract().response().asString();
		
//		to parse the String and create a json out of it
		JsonPath js=Reusable.rawToJson(response);
		
		String placeId=js.getString("place_id");
		
		System.out.println(placeId);
		
//		*********************** UPDATE PLACE API ***********************
		
		String newAddress="Bhumkar Chowk, India";
		
		given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
//				to replace the value with the variable, write exactly : "+placeId+"
				+ "\"place_id\":\""+placeId+"\",\r\n"
//				+ "\"place_id\":\"17d38de7136e8d8237d8390f0d1bec68\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "")
		.when().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		

//		*********************** GET PLACE API ***********************
		String response2 = given().log().all().queryParam("key","qaclick123").queryParam("place_id", placeId)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js1=Reusable.rawToJson(response2);
		
		String updatedAddress=js1.getString("address");
		
		Assert.assertEquals(newAddress, updatedAddress);
		
		System.out.println(updatedAddress);
		
		
		
		
		
		

	}

}
