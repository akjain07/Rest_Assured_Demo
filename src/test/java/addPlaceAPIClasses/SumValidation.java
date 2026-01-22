package addPlaceAPIClasses;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {
	
	@Test
	public void sumOfCourses() {

		JsonPath js = new JsonPath(Payload.CoursePrice());
		int count = js.getInt("courses.size()");
		 int total=0;
		for(int i=0;i<count;i++) {
			int price=js.getInt("courses["+i+"].price");
			int copies=js.getInt("courses["+i+"].copies");
			int amount=price*copies;
			total+=amount;
		}
		System.out.println(total);
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(purchaseAmount, total);
	}

}
