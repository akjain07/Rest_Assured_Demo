package POJOClasses;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

public class OAuth_Authentication_POJOClass {

	public static void main(String[] args) {
		
//		*********************** AUTHENTICATION SERVER API ***********************
		
		String response = given().log().all()
		.formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type", "client_credentials")
		.formParam("scope", "trust")
		.when().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
				
		
		JsonPath js=new JsonPath(response);
		String accessToken = js.getString("access_token");
//		System.out.println(accessToken);
		
//		*********************** GET COURSE DETAILS API ***********************
		
		GetCourse gc = given().log().all()
		.queryParam("access_token", accessToken)
		.when().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
		.then().log().all()
//		as(GetCourse.class) => Convert the JSON response body into a Java object of type GetCourse
		.extract().response().as(GetCourse.class);	
		
		System.out.println(gc.getInstructor());
		
//		print the price of course 'Rest Assured Automation using Java'
		List<Api> apiCourses =gc.getCourses().getApi();
		for(int i=0;i<apiCourses.size();i++) {
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("Rest Assured Automation using Java")) {
				System.out.println(apiCourses.get(i).getPrice());
			}
		}
		
		String[] expectedTitles= {"Selenium Webdriver Java","Cypress","Protractor"};
		
//		print all the course title in the WebAutomation
		List<webAutomation> webCourses =gc.getCourses().getWebAutomation();
		ArrayList<String> actual =new ArrayList<String>();
		for(int i=0;i<webCourses.size();i++) {
			String title=webCourses.get(i).getCourseTitle();
			actual.add(title);
		}
		
		List<String> expectedList = Arrays.asList(expectedTitles);
		
		Assert.assertTrue(expectedList.equals(actual));
		
		

	}

}
