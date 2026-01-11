package day8;

import static io.restassured.RestAssured.given;

import org.testng.ITestContext;
import org.testng.annotations.Test;

public class DeleteUserTest {

	static final String BASE_URL = "https://gorest.co.in/public/v2/users";
    static final String BEARER_TOKEN = "f0e8a1429cb89e2c097b00a1bf8e695b1ec3df0a75d0fc6c30ebf9b8c3b56ab9";

    
	@Test(dependsOnMethods= {"day8.UpdateUserTest.updateUser"})
	void deleteUser(ITestContext context)
	{
		given()
			.headers("Authorization", "Bearer "+BEARER_TOKEN)
			.pathParam("id", (Integer)context.getAttribute("userId"))
		.when()
			.delete(BASE_URL+"/{id}")
		.then()
		.statusCode(204);
	}
	
	
}
