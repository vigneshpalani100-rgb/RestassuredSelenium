package day6;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class ParsingJSONObject_BookStoreAPI {

	@Test
	void testJsonResponseBody()
	{
		
		ResponseBody responseBody=given()
		.when()
			.get("http://localhost:3000/store")
		.then()
			.statusCode(200)
			.extract().response().body();
		
		JsonPath jsonpath=new JsonPath(responseBody.asString());
		
		//Get the size of the json array
		int bookCount=jsonpath.getInt("book.size()");
	
		
		//validate at least one book should be in store
		assertThat(bookCount, greaterThan(0));
		
		//Print all the titles of books in a store
		for(int i=0; i<bookCount;i++)
		{
			String bookTitle=jsonpath.getString("book["+i+"].title");
			System.out.println(bookTitle);
		}
		
		
		//Search for a book
		boolean status=false;
		for(int i=0; i<bookCount;i++)
		{
			String bookTitle=jsonpath.getString("book["+i+"].title");
			if(bookTitle.equals("The Lord of the Rings"))
			{
				status=true;
				break;
			}
		}
		assertThat(status, is(true));
		
		
		//calculate and validate the total price of all the books
		double totalPrice=0;
		for(int i=0; i<bookCount;i++)
		{
			double bookPrice=jsonpath.getDouble("book["+i+"].price");
			totalPrice=totalPrice+bookPrice;
		}
		System.out.println("Total price of books: "+totalPrice);
		
		assertThat(totalPrice, is(53.92));
		
	}
}








