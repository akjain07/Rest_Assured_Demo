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
				.addHeader("Authorization", token)
				.setContentType(ContentType.JSON)
				.build();
		
		OrderDetails_POJO orderDetails=new OrderDetails_POJO();
		orderDetails.setCountry("India");
		orderDetails.setProductOrderedId(productId);
		
		List<OrderDetails_POJO> orderList=new ArrayList<OrderDetails_POJO>();
		orderList.add(orderDetails);
		
		OrderRequest_POJO placeOrder=new OrderRequest_POJO();
		placeOrder.setOrders(orderList);
		
		RequestSpecification placeOrderReq=given().log().all().spec(placeOrderBaseReq).body(placeOrder);
		
		String placeOrderResponse = placeOrderReq.when().post("/api/ecom/order/create-order")
		.then().log().all().extract().response().asString();
		
		System.out.println(placeOrderResponse);
		
		JsonPath js1=new JsonPath(placeOrderResponse);
		String orderId=js1.getString("orders[0]");
		
		System.out.println(orderId);

		
//		*********************** VIEW ORDER API ***********************
		
		RequestSpecification viewOrderBaseReq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token)
				.build();
		
		String viewOrderResp = given().log().all().spec(viewOrderBaseReq).queryParam("id",orderId)
		.when().get("/api/ecom/order/get-orders-details")
		.then().log().all().extract().response().asString();
		
		System.out.println(viewOrderResp);
		
		
//		*********************** DELETE ORDER API ***********************
		
		RequestSpecification deleteOrderBaseReq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token)
				.build();
		
//		relaxedHTTPSValidation() in Rest Assured is used to bypass SSL certificate validation, 
//		mainly for testing APIs hosted on environments with invalid or self-signed certificates
		RequestSpecification deleteOrderReq = given().relaxedHTTPSValidation().log().all().spec(deleteOrderBaseReq).pathParam("productId",productId);
		
//		to make the product id generic, we need to encapsulate it inside {} so at runtime, the value will be placed
		String deleteOrderResp = deleteOrderReq.when().delete("/api/ecom/product/delete-product/{productId}").then()
		.log().all().extract().response().asString();
		
		System.out.println(deleteOrderResp);
	
	
	
	}

}
