package day4;

import java.io.File;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class Internet {
	@Test
    void uploadSingleFile() {

        File file = new File("C:\\Users\\user\\Downloads\\notes1.txt");

        given()
            .multiPart("file", file)
            .contentType("multipart/form-data")
        .when()
            .post("https://the-internet.herokuapp.com/upload")
        .then()
            .statusCode(200)
            .body(containsString("File Uploaded!"))
            .body(containsString("notes1.txt"));

}
	
	
}

