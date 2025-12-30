package day2;





import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;


//POJO-plain old java project
// this is the class maintain data in the form of object

public class createstudentexternalfile {

	String studentId;
	String Name;
	String Location;
	String Phone;
	String courses[];
	
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String[] getCourses() {
		return courses;
	}

	public void setCourses(String[] courses) {
		this.courses = courses;
	}

	
	@Test
	void createStudentUsingexternalfile() throws FileNotFoundException
	{
		File myfile=new File(".\\src\\test\\java\\day2\\body.json");
		FileReader fileReader=new FileReader(myfile);
		JSONTokener jsonTokener=new JSONTokener(fileReader);
		
		JSONObject requestBody=new JSONObject(jsonTokener);
	
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
	
	

//@AfterMethod
void deleteStudentRecord()
{
	given()
			
	.when()
		.delete("http://localhost:3000/students/" + studentId)
	.then()
		.statusCode(200);
}
}


