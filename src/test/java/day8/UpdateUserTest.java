package day8;

import static io.restassured.RestAssured.*;

import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

public class UpdateUserTest {

	static final String BASE_URL = "https://gorest.co.in/public/v2/users";
    static final String BEARER_TOKEN = "f0e8a1429cb89e2c097b00a1bf8e695b1ec3df0a75d0fc6c30ebf9b8c3b56ab9";

	
	@Test(dependsOnMethods= {"day8.GetUserTest.getUser"})
	void updateUser(ITestContext context)
	{
		JSONObject requestData=new JSONObject();
		
		Faker faker = new Faker();
	       
		requestData.put("name",faker.name().fullName());
		requestData.put("gender","Male");
		requestData.put("email",faker.internet().emailAddress());
		requestData.put("status","active");
		
		given()
			.headers("Authorization", "Bearer "+BEARER_TOKEN)
			.contentType("application/json")
			.body(requestData.toString())
			.pathParam("id", (Integer)context.getAttribute("userId"))
		.when()
			.put(BASE_URL+"/{id}")
		.then()
			.statusCode(200)
			.log().body();
			
	}
	
}
