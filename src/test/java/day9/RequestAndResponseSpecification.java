package day9;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


/*
http://localhost:3000/employees
http://localhost:3000/employees/1

http://localhost:3000/employees?last_name=King
http://localhost:3000/employees?first_name=Steven

http://localhost:3000/employees?gender=Female
http://localhost:3000/employees?gender=Male
*/


public class RequestAndResponseSpecification {
	
    RequestSpecification httpRequest;
    ResponseSpecification httpResponse;

	@BeforeClass
	public void setup()
	{
		//Create request specification
		RequestSpecBuilder reqBuilder=new RequestSpecBuilder();
			reqBuilder.setBaseUri("http://localhost:3000");
			reqBuilder.setBasePath("/employees");
				
		httpRequest=reqBuilder.build();
		
		//Create response specification
		ResponseSpecBuilder resBuilder=new ResponseSpecBuilder();
			resBuilder.expectStatusCode(200);
			resBuilder.expectHeader("Content-Type", equalTo("application/json"));
									
		httpResponse=resBuilder.build();
		
	}
	
	
	@Test(priority=1)
	void getAllEmployees()
	{
		given()
			.spec(httpRequest)
		.when()
			.get()
		.then()
			.spec(httpResponse)
			.body("size()", greaterThan(0))
			.log().body();
	}
	
	@Test(priority=2)
	void getMaleEmployees()
	{
		given()
			.spec(httpRequest)
			.queryParam("gender","Male")
		.when()
			.get()
		.then()
			.spec(httpResponse)
			.body("gender", everyItem(equalTo("Male")))
			.log().body();
	}
	
	@Test(priority=3)
	void getFeMaleEmployees()
	{
		given()
			.spec(httpRequest)
			.queryParam("gender","Female")
		.when()
			.get()
		.then()
			.spec(httpResponse)
			.body("gender", everyItem(equalTo("Female")))
			.log().body();
	}
	
	 @Test(priority = 4)
	    public void getEmployeeById() {
	        given()
	            .spec(httpRequest)
	        .when()
	            .get("/1")
	        .then()
	            .spec(httpResponse)
	            .body("id", equalTo("1"));
	    }

	    @Test(priority = 5)
	    public void getEmployeeByFirstName() {
	        given()
	            .spec(httpRequest)
	            .queryParam("first_name", "Steven")
	        .when()
	            .get()
	        .then()
	            .spec(httpResponse)
	            .body("first_name", everyItem(equalTo("Steven")));
	    }

	    @Test(priority = 6)
	    public void getEmployeeByLastName() {
	        given()
	            .spec(httpRequest)
	            .queryParam("last_name", "King")
	        .when()
	            .get()
	        .then()
	            .spec(httpResponse)
	            .body("last_name", everyItem(equalTo("King")));
	    }
}

















