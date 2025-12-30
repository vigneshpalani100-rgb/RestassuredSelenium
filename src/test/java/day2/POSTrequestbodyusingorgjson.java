package day2;



import org.json.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/*
 * Different ways to create post request body/ request payload.
---------------
1) HashMap
2) org.json library
3) Using java POJO class
4) External json file

 */
public class POSTrequestbodyusingorgjson {

	
String studentId;
	
	//1. create post request body using HashMap
	
	@Test
	void createStudentUsingJsonLibrary()
	{
		
		JSONObject requestBody=new JSONObject();
		requestBody.put("name","Scott");
		requestBody.put("location","France");
		requestBody.put("phone","123456");
		
		String courses[]= {"C","C++"};
		requestBody.put("courses",courses);

		studentId=given()
				.contentType("application/json")
				.body(requestBody.toString())
			
			.when()
				.post("http://localhost:3000/students")
			.then()
				.statusCode(201)
				.body("name", equalTo("Scott"))
				.body("location", equalTo("France"))
				.body("phone", equalTo("123456"))
				.body("courses[0]", equalTo("C"))
				.body("courses[1]", equalTo("C++"))
				.header("Content-Type","application/json")
				.log().body()
				.extract().jsonPath().getString("id");
				
		}

	
	
	@AfterMethod
	void deleteStudentRecord()
	{
		given()
				
		.when()
			.delete("http://localhost:3000/students/" + studentId)
		.then()
			.statusCode(200);
	}
}




