package day3;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Arrays;

/*
1) From the application (Manual process)
1) Client ID
2) Client Secrete

2) Send Post request for getting token
POST https://api.imgur.com/oauth2/token
	ClientID
	Client secrete
	tokenURL
	Redirect URL
	Grant type
	Authorization code

you will get token once POST request is succesfull.

3) Use Token to do API call ( Get request).

*/

public class OAuth2AuthenticationDemo {

	@Test
	void verifyOAuth2Authentication() {

		String clientId = "Ov23lims65JDHgZX9Lqq";
		String clientSecret = "851ac42a9e5b70612fd0f7f89b225a2da8ee6abf";
		String redirectUri = "https://oauth.pstmn.io/v1/callback";
		String grantType = "authorization_code";
		 // Replace with actual authorization code
		String authorizationCode = "2b20e25e991d257b9ff0";
		
		
		String token=given()
				.header("Accept", "application/json")  // ✅ VERY IMPORTANT
			.formParam("client_id",clientId)
			.formParam("client_secret", clientSecret)
			.formParam("grant_type", grantType)
            .formParam("code", authorizationCode) 
            .formParam("redirect_uri", redirectUri)
        
		.when()
			.post("https://github.com/login/oauth/access_token")
		.then()
			.statusCode(200)
			.extract().jsonPath().getString("access_token");
			
			
		System.out.println("Generated token: "+token);
		
		given()
			.auth().oauth2(token)   // OAuth2 authentication
		.when()
			.get("https://api.github.com/user")
		.then()
			.statusCode(200)
			.log().body();
		
	}

}
