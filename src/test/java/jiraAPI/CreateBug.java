package jiraAPI;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

public class CreateBug {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI="https://ankitkooljain.atlassian.net/";
		
//		*********************** CREATE BUG API ***********************
		
		String newBugResponse = given().log().all()
		.header("Content-Type","application/json")
		.header("Authorization","Basic YW5raXRrb29samFpbkBnbWFpbC5jb206QVRBVFQzeEZmR0YwbDRNbWtveGFuMjFhcy1tZXlaNk14ZHFfVEJuTTFlRVZLVW1KaFdYbzVEUzI4WEgwOFd0OFdpdlZzSVJFWXVzQmJodFBiM1dpR2ZWTmhaRUl5ekk2ZUY0aThmNmxUZS1QV01hdS01ZmZhcTV6TnJuenZqWkdMLURjTHJTMVp5UmdXR2JkTWlaREctT1pLdDJ6eDNvaHlEa05FOVJVazEzb2sxVWg3QWhoOU1nPUVBODU0QzU2")
		.body("{\r\n"
				+ "\"fields\": {\r\n"
				+ "\"project\": {\r\n"
				+ "\"key\": \"RA\"\r\n"
				+ "},\r\n"
				+ "\"issuetype\": {\r\n"
				+ "\"name\": \"Bug\"\r\n"
				+ "},\r\n"
				+ "\"summary\": \"Bug 3 - Button not visible\",\r\n"
				+ "\"description\": [\"value\",\"This is test desc\"]\r\n"
				+ "}\r\n"
				+ "}}")
		.when().post("rest/api/3/issue")
		.then().assertThat().statusCode(201)
		.log().all()
		.extract().response().asString();
		
		JsonPath js=new JsonPath(newBugResponse);
		String bugId=js.getString("id");
		System.out.println(bugId);
		
//		*********************** ADD ATTACHMENT TO BUG ***********************
		
		given().log().all().pathParam("key", bugId)
		.header("X-Atlassian-Token","no-check")
		.header("Authorization","Basic YW5raXRrb29samFpbkBnbWFpbC5jb206QVRBVFQzeEZmR0YwbDRNbWtveGFuMjFhcy1tZXlaNk14ZHFfVEJuTTFlRVZLVW1KaFdYbzVEUzI4WEgwOFd0OFdpdlZzSVJFWXVzQmJodFBiM1dpR2ZWTmhaRUl5ekk2ZUY0aThmNmxUZS1QV01hdS01ZmZhcTV6TnJuenZqWkdMLURjTHJTMVp5UmdXR2JkTWlaREctT1pLdDJ6eDNvaHlEa05FOVJVazEzb2sxVWg3QWhoOU1nPUVBODU0QzU2")
		.multiPart("file",new File("D:\\Passport.jpeg"))
		.when().post("rest/api/3/issue/{key}/attachments")
		.then().log().all()
		.assertThat().statusCode(200);
		
		
		
		
		
		
	}

}
