package day1;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;


/* BDD style:
 * precondition:---given()---->content type,set cookies,add auth,add param,set header info etc...
 * Action/steps:---when()---->get,post,put,delete
 * validation----then()----->validate status code,extract response,extract header cookies & response body..
 * 
 * validations
 * status code
 * Response body
 * Response time
 * content-type
 * Response string
 */
public class HttpMethodsDemoreqres {

	int id;
//	1.test to retrieve users and validate response
	@Test(priority=1,enabled=true)
	void getUsers() {
		
		given()
		.when()
		.get("https://reqres.in/api/users?page=2")
		.then()
		.statusCode(200)
		.body("page", equalTo(2))
		.body(containsString("email"))
		.header("Content-Type", equalTo("application/json; charset=utf-8"))
		.time(lessThan(2000L))
		.log().all();
	}

	
//	2.test to retrieve users and validate response
	@Test(priority=2)
	void CreateUsers() {
		
		HashMap<String, Object> data = new HashMap<String,Object>();
		data.put("name", "morpheus");
		data.put("job", "leader");
				
		id=given()
		.contentType("application/json")
		.body(data)
		.when()
		.post("https://reqres.in/api/users?api_key=reqres-free-v1")
		.then()
		.statusCode(201)
		.header("Content-Type", equalTo("application/json; charset=utf-8"))
		.time(lessThan(2000L))
		.body("name",equalTo("morpheus"))
		.body("job",equalTo("leader"))
		.body("id", notNullValue())
		.log().all()
		.extract().jsonPath().getInt("id");
	}
	
	
//	3.test to update existing users and validate response
	@Test(priority=3,dependsOnMethods={"CreateUsers"})
	void UpdateUsers() {
		
		HashMap<String, Object> data = new HashMap<String,Object>();
		data.put("name", "sachin");
		data.put("job", "zion");
				
		given()
		.contentType("application/json")
		.body(data)
		.when()
		.put("https://reqres.in/api/users/2?api_key=reqres-free-v1")
		.then()
		.statusCode(200)
		.header("Content-Type", equalTo("application/json; charset=utf-8"))
		.time(lessThan(2000L))
		.body("name",equalTo("sachin"))
		.body("job",equalTo("zion"))
		.log().all();
	}
	
	
//	4.test to delete existing users and validate response
	@Test(priority=4,dependsOnMethods={"CreateUsers","UpdateUsers"})
	void DeleteUsers() {
		

		given()

		.when()
		.delete("https://reqres.in/api/users/2?api_key=reqres-free-v1")
		.then()
		.statusCode(204)
		.time(lessThan(2000L))

        .body(emptyOrNullString())
		.log().all();
	}
}
