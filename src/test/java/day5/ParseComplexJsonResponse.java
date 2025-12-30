package day5;

import io.restassured.path.json.JsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

public class ParseComplexJsonResponse {

	JSONObject getJsonResponse() 
	{
		File myfile=new File(".\\src\\test\\resources\\complex.json");
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(myfile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		JSONTokener jsonTokener=new JSONTokener(fileReader);
		
		JSONObject jsonResponse=new JSONObject(jsonTokener);
		return jsonResponse;
	}
	
    @Test(priority=1)
    public void testUserDetailsValidation() {
    			
        // Parse JSON response
        JsonPath jsonPath = new JsonPath(getJsonResponse().toString());
        
        // a) Verify status
        String status = jsonPath.getString("status");
        assertThat(status, is("success"));

        // b) Validate user details
        int id = jsonPath.getInt("data.userDetails.id");
        String name = jsonPath.getString("data.userDetails.name");
        String email = jsonPath.getString("data.userDetails.email");
        assertThat(id, is(12345));
        assertThat(name, is("John Doe"));
        assertThat(email, is("john.doe@example.com"));

        // c) Validate home phone number
        String homePhone = jsonPath.getString("data.userDetails.phoneNumbers[0].number");
        String homePhoneType = jsonPath.getString("data.userDetails.phoneNumbers[0].type");
        assertThat(homePhone, is("123-456-7890"));
        assertThat(homePhoneType, is("home"));

        // d) Validate geo coordinates
        double latitude = jsonPath.getDouble("data.userDetails.address.geo.latitude");
        double longitude = jsonPath.getDouble("data.userDetails.address.geo.longitude");
        assertThat(latitude, is(39.7817));
        assertThat(longitude, is(-89.6501));

        // e) Validate preferences
        boolean notifications = jsonPath.getBoolean("data.userDetails.preferences.notifications");
        String theme = jsonPath.getString("data.userDetails.preferences.theme");
        assertThat(notifications, is(true));
        assertThat(theme, is("dark"));
    }

    @Test(priority=2)
    public void testRecentOrdersValidation() {
    	// Parse JSON response
        JsonPath jsonPath = new JsonPath(getJsonResponse().toString());
        

        // a) Verify total number of orders
        int totalOrders = jsonPath.getInt("data.recentOrders.size()");
        assertThat(totalOrders, is(2));

        // b) Validate first order details
        int firstOrderId = jsonPath.getInt("data.recentOrders[0].orderId");
        double firstOrderTotal = jsonPath.getDouble("data.recentOrders[0].totalAmount");
        assertThat(firstOrderId, is(101));
        assertThat(firstOrderTotal, is(1226.49));

        // c) Validate second item in the first order
        String secondItemName = jsonPath.getString("data.recentOrders[0].items[1].name");
        double secondItemPrice = jsonPath.getDouble("data.recentOrders[0].items[1].price");
        assertThat(secondItemName, is("Mouse"));
        assertThat(secondItemPrice, is(25.50));

        // d) Validate second order details
        int secondOrderItems = jsonPath.getInt("data.recentOrders[1].items.size()");
        String secondOrderItemName = jsonPath.getString("data.recentOrders[1].items[0].name");
        double secondOrderItemPrice = jsonPath.getDouble("data.recentOrders[1].items[0].price");
        assertThat(secondOrderItems, is(1));
        assertThat(secondOrderItemName, is("Smartphone"));
        assertThat(secondOrderItemPrice, is(799.99));
    }

    @Test(priority=3)
    public void testPreferencesAndMetadataValidation() {
    	// Parse JSON response
        JsonPath jsonPath = new JsonPath(getJsonResponse().toString());
        

        // a) Validate languages
        int totalLanguages = jsonPath.getInt("data.userDetails.preferences.languages.size()");
        assertThat(totalLanguages, is(3));

        String firstLanguage = jsonPath.getString("data.userDetails.preferences.languages[0]");
        assertThat(firstLanguage, is("English"));

        // b) Validate metadata
        String requestId = jsonPath.getString("meta.requestId");
        int responseTime = jsonPath.getInt("meta.responseTimeMs");
        assertThat(requestId, is("abc123xyz"));
        assertThat(responseTime, lessThan(300));
    }
}
