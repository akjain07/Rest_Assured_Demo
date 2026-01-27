package addBookAPIClasses;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import files.Reusable;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {

	
//	*********************** ADD BOOK API ***********************
	@Test(dataProvider="getData")
	public void addBook(String isbn,String aisle) {
		
		RestAssured.baseURI="http://216.10.245.166";
		
		String response = given().log().all().header("Content-Type","application/json")
		.body(Payload.addBookData(isbn,aisle))
		
//		dynamically passing values to payload using external inputs
//		.body(Payload.addBookData("ccc","2345"))
		
		.when().post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js=Reusable.rawToJson(response);
		String id=js.getString("ID");
		System.out.println(id);
		
//		*********************** DELETE BOOK API ***********************
		
		given().log().all().header("Content-Type","application/json")
		.body(Payload.deleteBookData(id))
		.when().post("/Library/DeleteBook.php")
		.then().log().all().assertThat().statusCode(200);
	}	
	
	
	@DataProvider
	public Object[][] getData() {
		return new Object[][] {
			{"def","222"},
			{"efg","333"},
			{"fgh","444"}
		};
	}

}
