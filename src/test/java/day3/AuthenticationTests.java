package day3;


import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class AuthenticationTests {

	//1. Basic Authentication.
    
		//@Test
		void verifyBasicAuth()
		{
			given()
				.auth().basic("postman","password")
			.when()
				.get("https://postman-echo.com/basic-auth")
			.then()
				.statusCode(200)
				.body("authenticated", equalTo(true))
				.log().body();
		}
		
		
		//2. Basic Preemptive Authentication.
	    
		//@Test
		void verifyPreemtiveAuth()
		{
			given()
				.auth().preemptive().basic("postman", "password")
			.when()
				.get("https://postman-echo.com/basic-auth")
			.then()
				.statusCode(200)
				.body("authenticated", equalTo(true))
				.log().body();
		}
		
		//3. Digest Authentication.
	    
			//@Test
			void verifyDigestAuth()
			{
				given()
					.auth().digest("postman","password")
				.when()
					.get("https://postman-echo.com/basic-auth")
				.then()
					.statusCode(200)
					.body("authenticated", equalTo(true))
					.log().body();
			}
			
		
		//4. Bearer token authentication
			
			//@Test
			void verifyTokenAuth()
			{
				String bearerToken="example example";
				
				given()
					.header("Authorization","Bearer " +bearerToken)
				.when()
					.get("https://api.github.com/user/repos")
				.then()
					.statusCode(200)
					.log().body();
				
			}

			
			//5. API Key authentication	
			
			@Test
			void verifyAPIKeyAuth()
			{
				given()
					.queryParam("q","Delhi")
					.queryParam("appid","fe9c5cddb7e01d747b4611c3fc9eaf2c")
				.when()
					.get("https://api.openweathermap.org/data/2.5/weather")
				.then()
					.statusCode(200)
					.log().body();
			}
			
			
	}
