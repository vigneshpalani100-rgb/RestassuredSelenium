package day4;

import org.testng.annotations.Test;

import io.restassured.http.Cookie;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.Map;


public class CookiesTest {

	@Test
	void testCookiesInResponse()
	{
		
		Response response=given()
		
		.when()
			.get("https://www.google.com/")
		.then()
			.log().cookies()
			.statusCode(200)
			//.cookie("AEC","AZ6Zc-XQCdEv0ZJNFHpfDTuM5dGpaRN3fgnnY2gYcn5Zu3tpC7Al7JY3Nw");// cookie value validation - should fail
			.cookie("AEC", notNullValue())
			.extract().response();
		
		//Extract a specific cookie
		String cookieValue=response.getCookie("AEC");
		System.out.println("Value of 'AEC' Cookie: "+cookieValue);
		
		//Extract all the cookies
		Map<String, String>allCookies=response.getCookies();
		System.out.println("All the cookies: "+ allCookies);
		
		//Printing cookies and their values using for loop
		for(String key :allCookies.keySet())
		{
			System.out.println(key + ": " +allCookies.get(key));
		}
		
		
		//Get detailed information about cookie.
		Cookie cookie_info=response.getDetailedCookie("AEC");
		
		System.out.println(cookie_info.hasExpiryDate());
		System.out.println(cookie_info.getExpiryDate());
		System.out.println(cookie_info.hasValue());
		System.out.println(cookie_info.getValue());
		System.out.println(cookie_info.isSecured());
		
	}
		
}


