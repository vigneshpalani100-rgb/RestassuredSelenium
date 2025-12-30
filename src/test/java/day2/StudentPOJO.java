package day2;





import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;


//POJO-plain old java project
// this is the class maintain data in the form of object

public class StudentPOJO {

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
	void createStudentUsingPojoClass()
	{
		StudentPOJO requestBody=new StudentPOJO();
		requestBody.setName("Scott");
		requestBody.setLocation("France");
		requestBody.setPhone("123456");
		
		String courses[]= {"C","C++"};
		requestBody.setCourses(courses);
		
	
		studentId=given()
			.contentType("application/json")
			.body(requestBody)
		
		.when()
			.post("http://localhost:3000/students")
		.then()
			.statusCode(201)
			.body("name", equalTo(requestBody.getName()))
			.body("location", equalTo(requestBody.getLocation()))
			.body("phone", equalTo(requestBody.getPhone()))
			.body("courses[0]", equalTo(requestBody.getCourses()[0]))
			.body("courses[1]", equalTo(requestBody.getCourses()[1]))
			.header("Content-Type","application/json")
			.log().body()
			.extract().jsonPath().getString("id");
			
	}
	
//	@AfterMethod
	void deleteStudentRecord()
	{
		given()
				
		.when()
			.delete("http://localhost:3000/students/" + studentId)
		.then()
			.statusCode(200);
	}
	

}
