package addPlaceAPIClasses;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import files.Reusable;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Basic_StaticJson {

	public static void main(String[] args) throws IOException {
		

		RestAssured.baseURI="https://rahulshettyacademy.com";
		
//		*********************** ADD PLACE API ***********************
		
//		to pass the request from an external JSON file
		
//		first read the content of static json file >> convert the content into byte[] >> convert byte[] into string
		
//		read the file content and store file location as Path object
//		In Java, Path and Paths are classes from the java.nio.file package used for file and directory path handling.
		
//		Path is an interface that represents a file or directory path.
//		Paths is a utility class used to create Path objects.
		
		Path path = Paths.get("D:\\Study material\\RestAssured\\addplace.json");
		
//		convert the content into a byte[]
		byte[] byteContent = Files.readAllBytes(path);
		
//		convert the byte[] into string
		String requestFile =new String(byteContent);
		
		String response = given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body(requestFile)		
		.when().post("maps/api/place/add/json")	
		.then().log().all().assertThat().statusCode(200)
		.body("scope", equalTo("APP"))
		.header("Server", "Apache/2.4.52 (Ubuntu)")
		.extract().response().asString();
		
		JsonPath js=Reusable.rawToJson(response);		
		String placeId=js.getString("place_id");		
		System.out.println(placeId);
		

		

	}

}
