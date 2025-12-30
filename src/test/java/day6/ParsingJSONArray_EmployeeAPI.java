package day6;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class ParsingJSONArray_EmployeeAPI {

	@Test
	void testJsonResponseBody()
	{
		
		ResponseBody responseBody=given()
		.when()
			.get("http://localhost:3000/employees")
		.then()
			.statusCode(200)
			.extract().response().body();
		
		JsonPath jsonpath=new JsonPath(responseBody.asString());
		
		//Get the size of the json array
		int employeeCount=jsonpath.getInt("size()");
		
		// Print all the details of the employee
		for(int i=0;i<employeeCount;i++)
		{
			String firstName=jsonpath.getString("["+i+"].first_name");
			String lastName=jsonpath.getString("["+i+"].last_name");
			String email=jsonpath.getString("["+i+"].email");
			String gender=jsonpath.getString("["+i+"].gender");
			System.out.println(firstName+" "+lastName+"  "+email+"  "+gender);
		}
		
		//Search for an employee name "Steve" in the list
		boolean status=false;
		
		for(int i=0;i<employeeCount;i++)
		{
			String firstName=jsonpath.getString("["+i+"].first_name");
			if(firstName.equals("Steve"))
			{
				status=true;
				break;
			}
		}
		
		assertThat(status, is(true)); // Steven exists in the list or not
		
	}
}








