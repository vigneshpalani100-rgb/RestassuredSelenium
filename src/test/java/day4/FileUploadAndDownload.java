package day4;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;



public class FileUploadAndDownload {

	//1) single file upload
	
	@Test
	void uploadSingleFile()
	{
		File myfile=new File("C:\\Users\\user\\Downloads\\download.jpg");
		
		given()
			.multiPart("file",myfile)
			.contentType("multipart/form-data")
			
		.when()
			.post("http://localhost:8080/uploadFile")
		
		.then()
			.statusCode(200)
			.body("fileName", equalTo("download.jpg"))
			.log().body();
		
	}
	
	
	//2) Multiple files upload
	
	@Test
	void uploadMultipleFiles()
	{
		File myfile1=new File("C:\\Users\\user\\Downloads\\download.jpg");
		File myfile2=new File("C:\\Users\\user\\Downloads\\download1.jpg");
		
		given()
			.multiPart("files",myfile1)
			.multiPart("files",myfile2)
			.contentType("multipart/form-data")
			
		.when()
			.post("http://localhost:8080/uploadMultipleFiles")
		
		.then()
			.statusCode(200)
			.body("[0].fileName", equalTo("download.jpg"))
			.body("[1].fileName", equalTo("download1.jpg"))
			.log().body();
		
	}
	
	@Test
	void downloadFile()
	{
		given()
			.pathParam("filename","download.jpg")
		.when()
			.get("http://localhost:8080/downloadFile/{filename}")
			
		.then()
			.statusCode(200)
			.log().body();
		
	}
		
}
