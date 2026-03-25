package eCommerceE2E;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class E2ETest {
	public static void main(String[] args) {
		
//		*********************** LOGIN API ***********************
		
		RequestSpecification req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON)
				.build();
		
		LoginRequest_POJO loginRequest=new LoginRequest_POJO();
		loginRequest.setUserEmail("postman_akj@gmail.com");
		loginRequest.setUserPassword("Postman@123");
		
		RequestSpecification reqLogin=given().spec(req).body(loginRequest);
		
		LoginResponse_POJO loginResponse = reqLogin.log().all().when().post("/api/ecom/auth/login")
		.then().log().all().extract().response().as(LoginResponse_POJO.class);
		
		String token = loginResponse.getToken();
		String userId= loginResponse.getUserId();
		
		System.out.println(token);
		System.out.println(userId);
		
//		*********************** CREATE PRODUCT API ***********************
		
		RequestSpecification addProductBaseReq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token)
				.build();
				
		RequestSpecification reqCreateproduct = given().log().all().spec(addProductBaseReq).param("productName", "Keychain")
		.param("productAddedBy", userId)
		.param("productCategory", "fashion")
		.param("productSubCategory", "shirts")
		.param("productPrice", "500")
		.param("productDescription", "Key chain hai")
		.param("productFor", "Swag")
		.multiPart("productImage",new File("C:\\Users\\ankit\\Downloads\\ecomm.png"));
		
		String addProductResponse = reqCreateproduct.when().post("/api/ecom/product/add-product").then().log().all()
		.extract().response().asString();
		
		JsonPath js=new JsonPath(addProductResponse);
		String productId= js.getString("productId");
		
		
//		*********************** PLACE ORDER API ***********************
		
		RequestSpecification placeOrderBaseReq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON)
				.build();
		
		OrderDetails_POJO orderDetails=new OrderDetails_POJO();
		orderDetails.setCountry("India");
		orderDetails.setProductOrderId(productId);
		
		List<OrderDetails_POJO> orderList=new ArrayList<OrderDetails_POJO>();
		orderList.add(orderDetails);
		
		PlaceOrderRequest_POJO placeOrder=new PlaceOrderRequest_POJO();
		placeOrder.setOrders(orderList);
		
		RequestSpecification placeOrderReq=given().log().all().spec(placeOrderBaseReq).body(placeOrder);
		
		String placeOrderResponse = placeOrderReq.when().post("/api/ecom/order/create-order")
		.then().log().all().extract().response().asString();

	
	
	
	}

}
