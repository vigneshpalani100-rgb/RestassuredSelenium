package day7;

import static io.restassured.RestAssured.given;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;
import io.restassured.matcher.RestAssuredMatchers;


public class SchemaValidations {
	
	//JsonSchema validation
//	@Test(priority=1)
	void testJsonSchema()
	{
		given()
		.when()
			.get("https://mocktarget.apigee.net/json")
		.then()
			.assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonSchema.json"));
		
	}

	
	
	//Xml schema/XSD validation

	@Test(priority=1)
	void testxmlSchema()
	{
		given()
		.when()
			.get("https://mocktarget.apigee.net/xml")
		.then()
			.assertThat().body(RestAssuredMatchers.matchesXsdInClasspath("xmlSchema.xsd"));
		
	}
}
