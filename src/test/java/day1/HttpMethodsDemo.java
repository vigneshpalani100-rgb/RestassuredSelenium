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
public class HttpMethodsDemo {

	int id;
//	1.test to retrieve users and validate response
	@Test(priority=1,enabled=false)
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
		data.put("title", "foo");
		data.put("body", "bar");
		data.put("userId", 1);
		
		id=given()
		.contentType("application/json")
		.body(data)
		.when()
		.post("https://jsonplaceholder.typicode.com/posts")
		.then()
		.statusCode(201)
		.header("Content-Type", equalTo("application/json; charset=utf-8"))
		.time(lessThan(2000L))
		.body("title",equalTo("foo"))
		.body("body",equalTo("bar"))
		.body("userId", equalTo(1))
        .body("id", notNullValue())
		.log().all()
		.extract().jsonPath().getInt("id");
	}
	
	
//	3.test to update existing users and validate response
	@Test(priority=3,dependsOnMethods={"CreateUsers"})
	void UpdateUsers() {
		
		HashMap<String, Object> data = new HashMap<String,Object>();
		data.put("title", "sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
		data.put("body", "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam");
		data.put("userId", 1);
		data.put("id", 1);
		
		given()
		.contentType("application/json")
		.body(data)
		.when()
		.put("https://jsonplaceholder.typicode.com/posts/1")
		.then()
		.statusCode(200)
		.header("Content-Type", equalTo("application/json; charset=utf-8"))
		.time(lessThan(2000L))
		.body("title",equalTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"))
		.body("body",equalTo("quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam"))
		.body("userId", equalTo(1))
        .body("id", equalTo(1))
		.log().all();
	}
	
	
//	4.test to delete existing users and validate response
	@Test(priority=4,dependsOnMethods={"CreateUsers","UpdateUsers"})
	void DeleteUsers() {
		

		given()

		.when()
		.delete("https://jsonplaceholder.typicode.com/posts/1")
		.then()
		.statusCode(200)
		.time(lessThan(2000L))

        .body(emptyArray())
		.log().all();
	}
}
