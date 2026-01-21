package addPlaceAPIClasses;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static org.hamcrest.Matchers.*;
import org.testng.Assert;
import files.Payload;
import files.Reusable;
import static io.restassured.RestAssured.*;

public class Basic_Original2 {

	public static void main(String[] args) {
		

		RestAssured.baseURI="https://rahulshettyacademy.com";
		
//		*********************** ADD PLACE API ***********************
		
		String response = given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body(Payload.AddPlace())		
		.when().post("maps/api/place/add/json")	
		.then().log().all().assertThat().statusCode(200)
		.body("scope", equalTo("APP"))
		.header("Server", "Apache/2.4.52 (Ubuntu)")
		.extract().response().asString();
		JsonPath js=Reusable.rawToJson(response);		
		String placeId=js.getString("place_id");		
		System.out.println(placeId);
		
//		*********************** UPDATE PLACE API ***********************
		
		String newAddress="Bhumkar Chowk, India";
		
		given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
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
