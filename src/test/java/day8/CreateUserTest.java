package day8;

import org.testng.ITestContext;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import org.json.JSONObject;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;


public class CreateUserTest {

	static final String BASE_URL = "https://gorest.co.in/public/v2/users";
	static final String BEARER_TOKEN = "f0e8a1429cb89e2c097b00a1bf8e695b1ec3df0a75d0fc6c30ebf9b8c3b56ab9";
	int userId;
	
	Faker faker=new Faker();
	
	@Test
	void createUser(ITestContext context)
	{
		JSONObject requestData=new JSONObject();
		requestData.put("name",faker.name().fullName());
		requestData.put("gender","Male");
		requestData.put("email",faker.internet().emailAddress());
		requestData.put("status","inactive");
		
				
		userId=given()
			.headers("Authorization", "Bearer "+BEARER_TOKEN)
			.contentType("application/json")
			.body(requestData.toString())
		.when()
			.post(BASE_URL)
		.then()
			.statusCode(201)
			.log().body()
			.extract().response().jsonPath().getInt("id");
		
		context.setAttribute("userId",userId);
	}
	
}
