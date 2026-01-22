package addPlaceAPIClasses;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JsonPath js = new JsonPath(Payload.CoursePrice());

//		Q1) Print no of courses returned by API
		int count = js.getInt("courses.size()");
		System.out.println(count);

//		Q2) Print the purchase amount
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);
		
//		Q3) Print the title of the first course
		String titleFirstCourse = js.get("courses[0].title");
		System.out.println(titleFirstCourse);
		
//		Q4) Print all the course titles and their respective price
		for(int i=0;i<count;i++) {
			String titles=js.get("courses["+i+"].title");
			System.out.println(titles);
			System.out.println(js.get("courses["+i+"].price").toString());
			
//			can't work this way
//			System.out.println(js.get("courses["+i+"].title"));
			
		}
		
//		Q5) Print no of copies sold by RPA course
		for(int i=0;i<count;i++) {
			String titles=js.get("courses["+i+"].title");
			if(titles.equalsIgnoreCase("rpa")) {
				int copies = js.getInt("courses["+i+"].copies");
				System.out.println(copies);
				break;
			}
			
		}

	}

}
